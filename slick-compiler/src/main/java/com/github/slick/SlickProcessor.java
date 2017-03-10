package com.github.slick;

import com.github.slick.components.AddMethodGenerator;
import com.github.slick.components.AddMethodGeneratorCallbackImpl;
import com.github.slick.components.BindMethodBodyGenerator;
import com.github.slick.components.BindMethodBodyGeneratorFragmentImpl;
import com.github.slick.components.BindMethodBodyGeneratorImpl;
import com.github.slick.components.GetViewIdGenerator;
import com.github.slick.components.GetViewIdGeneratorConductorImpl;
import com.github.slick.components.GetViewIdGeneratorImpl;
import com.github.slick.components.MethodSignatureGenerator;
import com.github.slick.components.MethodSignatureGeneratorDaggerImpl;
import com.github.slick.components.MethodSignatureGeneratorImpl;
import com.github.slick.components.PresenterInstantiationGenerator;
import com.github.slick.components.PresenterInstantiationGeneratorDaggerImpl;
import com.github.slick.components.PresenterInstantiationGeneratorImpl;
import com.github.slick.components.ViewCallbackGenerator;
import com.github.slick.components.ViewCallbackGeneratorActivityImpl;
import com.github.slick.components.ViewCallbackGeneratorConductorImpl;
import com.github.slick.components.ViewCallbackGeneratorFragmentSupportImpl;
import com.github.slick.components.ViewCallbackGeneratorNoOpImpl;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import static com.github.slick.SlickProcessor.ViewType.ACTIVITY;
import static com.squareup.javapoet.ClassName.get;

@AutoService(Processor.class)
public class SlickProcessor extends AbstractProcessor {

    public enum ViewType {
        ACTIVITY(ClASS_NAME_ACTIVITY, CLASS_NAME_SLICK_DELEGATE_ACTIVITY),
        FRAGMENT(ClASS_NAME_FRAGMENT, CLASS_NAME_SLICK_DELEGATE_FRAGMENT),
        FRAGMENT_SUPPORT(ClASS_NAME_FRAGMENT_SUPPORT, CLASS_NAME_SLICK_DELEGATE_FRAGMENT_SUPPORT),
        CONDUCTOR(CLASS_NAME_CONTROLLER, CLASS_NAME_SLICK_DELEGATE_CONDUCTOR),
        VIEW(ClASS_NAME_VIEW, CLASS_NAME_SLICK_DELEGATE_VIEW),
        DAGGER_ACTIVITY(ClASS_NAME_ACTIVITY, CLASS_NAME_SLICK_DELEGATE_ACTIVITY),
        DAGGER_FRAGMENT(ClASS_NAME_FRAGMENT, CLASS_NAME_SLICK_DELEGATE_FRAGMENT),
        DAGGER_FRAGMENT_SUPPORT(ClASS_NAME_FRAGMENT_SUPPORT, CLASS_NAME_SLICK_DELEGATE_FRAGMENT_SUPPORT),
        DAGGER_CONDUCTOR(CLASS_NAME_CONTROLLER, CLASS_NAME_SLICK_DELEGATE_CONDUCTOR),
        DAGGER_VIEW(ClASS_NAME_VIEW, CLASS_NAME_SLICK_DELEGATE_VIEW),
        UNSUPPORTED(null, null);

        private final ClassName className;
        private final ClassName delegateType;

        public ClassName className() {
            return className;
        }

        public ClassName delegateType() {
            return delegateType;
        }

        ViewType(ClassName className, ClassName delegateType) {
            this.className = className;
            this.delegateType = delegateType;
        }
    }

    private static final String pkgName = "com.github.slick";
    static final ClassName ClASS_NAME_ACTIVITY = get("android.app", "Activity");
    static final ClassName ClASS_NAME_FRAGMENT = get("android.app", "Fragment");
    static final ClassName ClASS_NAME_FRAGMENT_SUPPORT = get("android.support.v4.app", "Fragment");
    static final ClassName ClASS_NAME_VIEW = get("android.view", "View");
    static final ClassName CLASS_NAME_CONTROLLER = get("com.bluelinelabs.conductor", "Controller");
    static final ClassName ClASS_NAME_HASH_MAP = get("java.util", "HashMap");
    static final ClassName ClASS_NAME_STRING = get("java.lang", "String");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_ACTIVITY = get(pkgName, "SlickDelegateActivity");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_FRAGMENT = get(pkgName, "SlickDelegateFragment");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_VIEW = get(pkgName, "SlickDelegateView");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_FRAGMENT_SUPPORT = get("com.github.slick.supportfragment", "SlickDelegateFragment");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_CONDUCTOR = get("com.github.slick.conductor", "SlickDelegateConductor");
    static final ClassName ClASS_NAME_ON_DESTROY_LISTENER = get(pkgName, "OnDestroyListener");

    private Filer filer;
    private Messager messager;
    private Types typeUtils;
    private MethodSignatureGenerator msg = new MethodSignatureGeneratorImpl();
    private MethodSignatureGenerator msgDagger = new MethodSignatureGeneratorDaggerImpl();
    private GetViewIdGenerator gvig = new GetViewIdGeneratorImpl();
    private GetViewIdGenerator gvigConductor = new GetViewIdGeneratorConductorImpl();
    private PresenterInstantiationGenerator pig = new PresenterInstantiationGeneratorImpl();
    private PresenterInstantiationGenerator pigDagger = new PresenterInstantiationGeneratorDaggerImpl();
    private ViewCallbackGenerator vcgActivity = new ViewCallbackGeneratorActivityImpl();
    private ViewCallbackGenerator vcgNoOp = new ViewCallbackGeneratorNoOpImpl();
    private ViewCallbackGenerator vcgFragmentSupport = new ViewCallbackGeneratorFragmentSupportImpl();
    private ViewCallbackGenerator vcgConductor = new ViewCallbackGeneratorConductorImpl();
    private BindMethodBodyGenerator bmbgActivity = new BindMethodBodyGeneratorImpl(gvig, pig, vcgActivity);
    private BindMethodBodyGenerator bmbgActivityDagger = new BindMethodBodyGeneratorImpl(gvig, pigDagger, vcgActivity);
    private BindMethodBodyGenerator bmbgFragment = new BindMethodBodyGeneratorFragmentImpl(gvig, pig, vcgNoOp);
    private BindMethodBodyGenerator bmbgFragmentDagger = new BindMethodBodyGeneratorFragmentImpl(gvig, pigDagger, vcgNoOp);
    private BindMethodBodyGenerator bmbgFragmentSupport = new BindMethodBodyGeneratorImpl(gvig, pig, vcgFragmentSupport);
    private BindMethodBodyGenerator bmbgFragmentSupportDagger = new BindMethodBodyGeneratorImpl(gvig, pigDagger, vcgFragmentSupport);
    private BindMethodBodyGenerator bmbgConductor = new BindMethodBodyGeneratorImpl(gvigConductor, pig, vcgConductor);
    private BindMethodBodyGenerator bmbgConductorDagger = new BindMethodBodyGeneratorImpl(gvigConductor, pigDagger, vcgConductor);
    private BindMethodBodyGenerator bmbgView = new BindMethodBodyGeneratorImpl(gvig, pig, vcgNoOp);
    private AddMethodGenerator amgFragment = new AddMethodGeneratorCallbackImpl("onStart", "onStop", "onDestroy");
    private AddMethodGenerator amgView = new AddMethodGeneratorCallbackImpl("onAttach", "onDetach");
    private PresenterGenerator generatorActivity = new BasePresenterGeneratorImpl(msg, bmbgActivity);
    private PresenterGenerator generatorFragment = new BasePresenterGeneratorImpl(msg, bmbgFragment, amgFragment);
    private PresenterGenerator generatorFragmentSupport = new BasePresenterGeneratorImpl(msg, bmbgFragmentSupport);
    private PresenterGenerator generatorConductor = new BasePresenterGeneratorImpl(msg, bmbgConductor);
    private PresenterGenerator generatorView = new BasePresenterGeneratorImpl(msg, bmbgView, amgView);
    private PresenterGenerator generatorDaggerActivity = new BasePresenterGeneratorImpl(msgDagger, bmbgActivityDagger);
    private PresenterGenerator generatorDaggerFragment = new BasePresenterGeneratorImpl(msgDagger, bmbgFragmentDagger, amgFragment);
    private PresenterGenerator generatorDaggerFragmentSupport = new BasePresenterGeneratorImpl(msgDagger, bmbgFragmentSupportDagger);
    private PresenterGenerator generatorDaggerConductor = new BasePresenterGeneratorImpl(msgDagger, bmbgConductorDagger);

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        typeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedTypes = new LinkedHashSet<>();
        supportedTypes.add(Presenter.class.getCanonicalName());
        return supportedTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    // 2017-02-01 get the view name from super class parametrized type - DONE
    // 2017-02-01 fill the annotatedPresenter with more data - DONE
    // 2017-02-01 generate the constructors args - DONE
    // 2017-02-01 generate dagger delegate - DONE
    // 2017-02-01 generate host for fragment's presenter - DONE
    // 2017-02-01 refactor the code generating part to its own class - DONE
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Presenter.class);
        // TODO: 2017-02-23 extract this to a validator
        List<AnnotatedPresenter> annotatedPresenters = new ArrayList<>(elementsAnnotatedWith.size());
        for (Element element : elementsAnnotatedWith) {
            final TypeElement typeElement = (TypeElement) typeUtils.asElement(element.asType());
            final DeclaredType superclass = (DeclaredType) typeElement.getSuperclass();
            if (superclass == null) {
                error(element, "%s should extends SlickPresenter<SlickView>.", typeElement.getQualifiedName());
                continue;
            }
            final List<? extends TypeMirror> typeArguments = superclass.getTypeArguments();
            if (typeArguments == null || typeArguments.size() <= 0) {
                error(element, "%s should extends SlickPresenter<SlickView>, missing type argument.",
                        typeElement.getQualifiedName());
                continue;
            }

            try {
                annotatedPresenters.add(scanPresenter(element, typeElement, typeArguments));
            } catch (Exception e) {
                logParsingError(element, Presenter.class, e);
            }
        }
        for (AnnotatedPresenter annotatedPresenter : annotatedPresenters) {
            final TypeSpec cls = generatePresenterHost(annotatedPresenter);
            brewJava(annotatedPresenter.getPresenter().packageName(), cls);
        }

        /*final TypeSpec cls = generateSlickClass(annotatedPresenters);
        brewJava(pkgName, cls);*/


        return true;
    }

    protected void brewJava(String packageName, TypeSpec cls) {
        final JavaFile javaFile =
                JavaFile.builder(packageName, cls).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the Presenter Host class from provided information
     *
     * @param ap
     * @return TypeSpec
     */
    private TypeSpec generatePresenterHost(AnnotatedPresenter ap) {
        switch (ap.getViewType()) {
            case ACTIVITY:
                return generatorActivity.generate(ap);
            case FRAGMENT:
                return generatorFragment.generate(ap);
            case FRAGMENT_SUPPORT:
                return generatorFragmentSupport.generate(ap);
            case CONDUCTOR:
                return generatorConductor.generate(ap);
            case VIEW:
                return generatorView.generate(ap);
            case DAGGER_ACTIVITY:
                return generatorDaggerActivity.generate(ap);
            case DAGGER_FRAGMENT:
                return generatorDaggerFragment.generate(ap);
            case DAGGER_FRAGMENT_SUPPORT:
                return generatorDaggerFragmentSupport.generate(ap);
            case DAGGER_CONDUCTOR:
                return generatorDaggerConductor.generate(ap);
            default:
                throw new UnsupportedOperationException(ap.getViewType() + " Type");
        }

    }

    /**
     * Scans and gathers required information on the annotated presenter class
     *
     * @param typeElement
     * @param typeArguments
     * @return AnnotatedPresenter
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    private AnnotatedPresenter scanPresenter(Element element, TypeElement typeElement,
                                             List<? extends TypeMirror> typeArguments)
            throws IllegalArgumentException, IllegalStateException {
        final ClassName presenter = getClassName(typeElement);

        final String fieldName = element.getSimpleName().toString();

        final TypeElement viewTypeElement = (TypeElement) element.getEnclosingElement();
        ClassName viewTypeClassName = get(getViewType(typeElement, viewTypeElement));
        List<PresenterArgs> args = null;

        ViewType viewType = ViewType.UNSUPPORTED;
        String presenterProvider;
        if ((presenterProvider = findPresenterProvider(element)) != null) {
            if (ClASS_NAME_ACTIVITY.equals(viewTypeClassName)) {
                viewType = ViewType.DAGGER_ACTIVITY;
            } else if (CLASS_NAME_CONTROLLER.equals(viewTypeClassName)) {
                viewType = ViewType.DAGGER_CONDUCTOR;
            } else if (ClASS_NAME_FRAGMENT.equals(viewTypeClassName)) {
                viewType = ViewType.DAGGER_FRAGMENT;
            } else if (ClASS_NAME_FRAGMENT_SUPPORT.equals(viewTypeClassName)) {
                viewType = ViewType.DAGGER_FRAGMENT_SUPPORT;
            } else if (ClASS_NAME_VIEW.equals(viewTypeClassName)) {
                viewType = ViewType.DAGGER_VIEW;
            }

        } else {
            if (ClASS_NAME_ACTIVITY.equals(viewTypeClassName)) {
                viewType = ACTIVITY;
            } else if (CLASS_NAME_CONTROLLER.equals(viewTypeClassName)) {
                viewType = ViewType.CONDUCTOR;
            } else if (ClASS_NAME_FRAGMENT.equals(viewTypeClassName)) {
                viewType = ViewType.FRAGMENT;
            } else if (ClASS_NAME_FRAGMENT_SUPPORT.equals(viewTypeClassName)) {
                viewType = ViewType.FRAGMENT_SUPPORT;
            } else if (ClASS_NAME_VIEW.equals(viewTypeClassName)) {
                viewType = ViewType.VIEW;
            }

            args = scanPresenterArgs(typeElement);
        }

        return new AnnotatedPresenter(typeArguments.get(0).toString(), args, fieldName,
                getClassName(viewTypeElement), viewType, presenter, presenterProvider);

    }

    private List<PresenterArgs> scanPresenterArgs(TypeElement typeElement) {
        final List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        for (Element enclosedElement : enclosedElements) {
            if (ElementKind.CONSTRUCTOR.equals(enclosedElement.getKind())) {
                // TODO: 2017-02-01 restrict to one constructor only
                final ExecutableElement constructor = (ExecutableElement) enclosedElement;
                List<? extends VariableElement> parameters = constructor.getParameters();
                List<PresenterArgs> args = new ArrayList<>(parameters.size());
                for (VariableElement parameter : parameters) {
                    final List<? extends AnnotationMirror> annotationMirrors =
                            parameter.getAnnotationMirrors();

                    final PresenterArgs presenterArgs = new PresenterArgs(
                            parameter.getSimpleName().toString(),
                            parameter.asType(),
                            annotationMirrors);

                    args.add(presenterArgs);
                }
                return args;
            }
        }
        return new ArrayList<>(0);
    }

    private String findPresenterProvider(Element element) {
        for (Element sibling : element.getEnclosingElement().getEnclosedElements()) {
            if (sibling.getKind().equals(ElementKind.FIELD) &&
                    sibling.getAnnotation(Inject.class) != null &&
                    ParameterizedTypeName.get(get(Provider.class), TypeName.get(element.asType()))
                            .equals(get(sibling.asType()))) {
                return sibling.getSimpleName().toString();
            }
        }
        return null;
    }

    private ClassName getClassName(TypeElement typeElement) {
        return get(
                typeElement.asType().toString().replace("." + typeElement.getSimpleName().toString(), ""),
                typeElement.getSimpleName().toString());
    }

    private TypeElement getViewType(TypeElement typeElement, Element viewType) throws IllegalArgumentException {
        TypeElement viewTypeElement = (TypeElement) viewType;
        if (viewType == null) {
            error(typeElement, "@Presenter doesn't have the view class. @Presenter(YourActivityOrFragment.class)");
            throw new IllegalArgumentException("error");
        }
        while (true) {
            if (ClASS_NAME_ACTIVITY.toString().equals(viewTypeElement.toString()) ||
                    ClASS_NAME_FRAGMENT.toString().equals(viewTypeElement.toString()) ||
                    ClASS_NAME_FRAGMENT_SUPPORT.toString().equals(viewTypeElement.toString()) ||
                    CLASS_NAME_CONTROLLER.toString().equals(viewTypeElement.toString()) ||
                    ClASS_NAME_VIEW.toString().equals(viewTypeElement.toString())) {
                return viewTypeElement;
            }
            viewTypeElement = (TypeElement) typeUtils.asElement(viewTypeElement.getSuperclass());
            if (viewTypeElement == null) {
                error(typeElement, "View class should extends Activity, Fragment or View");
                throw new IllegalArgumentException("error");
            }
        }
    }

    private void logParsingError(Element element, Class<? extends Annotation> annotation, Exception e) {
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        error(element, "Unable to parse @%s.\n\n%s", annotation.getSimpleName(), stackTrace);
    }

    private void error(Element element, String message, Object... args) {
        printMessage(Diagnostic.Kind.ERROR, element, message, args);
    }

    private void note(Element element, String message, Object... args) {
        printMessage(Diagnostic.Kind.NOTE, element, message, args);
    }

    private void printMessage(Diagnostic.Kind kind, Element element, String message, Object[] args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(kind, message, element);
    }

    static String deCapitalize(String string) {
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }

}
