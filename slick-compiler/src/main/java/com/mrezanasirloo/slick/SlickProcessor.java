/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick;

import com.google.auto.service.AutoService;
import com.mrezanasirloo.slick.components.AddMethodGenerator;
import com.mrezanasirloo.slick.components.AddMethodGeneratorCallbackImpl;
import com.mrezanasirloo.slick.components.AddMethodGeneratorViewImpl;
import com.mrezanasirloo.slick.components.BindMethodBodyGenerator;
import com.mrezanasirloo.slick.components.BindMethodBodyGeneratorFragmentImpl;
import com.mrezanasirloo.slick.components.BindMethodBodyGeneratorImpl;
import com.mrezanasirloo.slick.components.GetViewIdGenerator;
import com.mrezanasirloo.slick.components.GetViewIdGeneratorConductorImpl;
import com.mrezanasirloo.slick.components.GetViewIdGeneratorImpl;
import com.mrezanasirloo.slick.components.MethodSignatureGenerator;
import com.mrezanasirloo.slick.components.MethodSignatureGeneratorDaggerImpl;
import com.mrezanasirloo.slick.components.MethodSignatureGeneratorImpl;
import com.mrezanasirloo.slick.components.PresenterInstantiationGenerator;
import com.mrezanasirloo.slick.components.PresenterInstantiationGeneratorDaggerImpl;
import com.mrezanasirloo.slick.components.PresenterInstantiationGeneratorImpl;
import com.mrezanasirloo.slick.components.ViewCallbackGenerator;
import com.mrezanasirloo.slick.components.ViewCallbackGeneratorActivityImpl;
import com.mrezanasirloo.slick.components.ViewCallbackGeneratorConductorImpl;
import com.mrezanasirloo.slick.components.ViewCallbackGeneratorFragmentSupportImpl;
import com.mrezanasirloo.slick.components.ViewCallbackGeneratorNoOpImpl;
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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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

import static com.mrezanasirloo.slick.SlickProcessor.ViewType.ACTIVITY;
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

    private static final String pkgName = "com.mrezanasirloo.slick";
    public static final ClassName ClASS_NAME_ON_DESTROY_LISTENER = get(pkgName, "SlickLifecycleListener");
    public static final ClassName ClASS_NAME_ACTIVITY = get("android.app", "Activity");
    public static final ClassName ClASS_NAME_VIEW = get("android.view", "View");
    static final ClassName ClASS_NAME_FRAGMENT = get("android.app", "Fragment");
    static final ClassName ClASS_NAME_FRAGMENT_SUPPORT = get("androidx.fragment.app", "Fragment");
    static final ClassName CLASS_NAME_CONTROLLER = get("com.bluelinelabs.conductor", "Controller");
    static final ClassName ClASS_NAME_HASH_MAP = get("android.util", "SparseArray");
    static final ClassName ClASS_NAME_STRING = get("java.lang", "String");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_ACTIVITY = get(pkgName, "SlickDelegateActivity");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_FRAGMENT = get(pkgName, "SlickDelegateFragment");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_VIEW = get(pkgName, "SlickDelegateView");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_FRAGMENT_SUPPORT = get(pkgName + ".supportfragment", "SlickDelegateFragment");
    static final ClassName CLASS_NAME_SLICK_DELEGATE_CONDUCTOR = get(pkgName + ".conductor", "SlickDelegateConductor");
    static final ClassName ClASS_NAME_INTERNAL_ON_DESTROY_LISTENER = get(pkgName, "InternalOnDestroyListener");

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
    private BindMethodBodyGenerator bmbgViewDagger = new BindMethodBodyGeneratorImpl(gvig, pigDagger, vcgNoOp);
    private AddMethodGenerator amgFragment = new AddMethodGeneratorCallbackImpl("onStart", "onStop", "onDestroy");
    private AddMethodGenerator amgView = new AddMethodGeneratorCallbackImpl("onAttach", "onDetach", "onDestroy");
    private AddMethodGenerator amgView2 = new AddMethodGeneratorViewImpl();
    private PresenterGenerator generatorActivity = new BasePresenterGeneratorImpl(msg, bmbgActivity);
    private PresenterGenerator generatorFragment = new BasePresenterGeneratorImpl(msg, bmbgFragment, amgFragment);
    private PresenterGenerator generatorFragmentSupport = new BasePresenterGeneratorImpl(msg, bmbgFragmentSupport);
    private PresenterGenerator generatorConductor = new BasePresenterGeneratorImpl(msg, bmbgConductor);
    private PresenterGenerator generatorView = new BasePresenterGeneratorImpl(msg, bmbgView, amgView, amgView2);
    private PresenterGenerator generatorDaggerView = new BasePresenterGeneratorImpl(msgDagger, bmbgViewDagger, amgView, amgView2);
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
        Map<ClassName, List<AnnotatedPresenter>> annotatedPresenters = new HashMap<>(elementsAnnotatedWith.size());
        for (Element element : elementsAnnotatedWith) {
            final TypeElement typeElement = (TypeElement) typeUtils.asElement(element.asType());
            final DeclaredType superclass = (DeclaredType) typeElement.getSuperclass();
            if (superclass == null) {
                error(element, "%s should extends SlickPresenter<ViewInterface>.", typeElement.getQualifiedName());
                continue;
            }
            final List<? extends TypeMirror> typeArguments = superclass.getTypeArguments();
            if (typeArguments == null || typeArguments.size() <= 0) {
                error(element, "%s should extends SlickPresenter<ViewInterface>, missing type argument.",
                        typeElement.getQualifiedName());
                continue;
            }

            try {
                AnnotatedPresenter annotatedPresenter = scanPresenter(element, typeElement, typeArguments);
                List<AnnotatedPresenter> list = annotatedPresenters.get(annotatedPresenter.getPresenter());
                if (list == null) list = new ArrayList<>();
                list.add(annotatedPresenter);
                annotatedPresenters.put(annotatedPresenter.getPresenter(), list);
            } catch (Exception e) {
                logParsingError(element, Presenter.class, e);
            }
        }
        for (List<AnnotatedPresenter> annotatedPresenterList : annotatedPresenters.values()) {
            AnnotatedPresenter annotatedPresenter = annotatedPresenterList.remove(0);
            final TypeSpec.Builder builder = generatePresenterHost(annotatedPresenter, false).toBuilder();
            for (AnnotatedPresenter ap : annotatedPresenterList) {
                final TypeSpec cls = generatePresenterHost(ap, true);
                builder.addMethods(cls.methodSpecs);
            }
            brewJava(annotatedPresenter.getPresenter().packageName(), builder.build());
        }
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
     * @param isMulti if this presenter is used by multiple views
     * @return TypeSpec
     */
    private TypeSpec generatePresenterHost(AnnotatedPresenter ap, boolean isMulti) {
        switch (ap.getViewType()) {
            case ACTIVITY:
                return generatorActivity.generate(ap, isMulti);
            case FRAGMENT:
                return generatorFragment.generate(ap, isMulti);
            case FRAGMENT_SUPPORT:
                return generatorFragmentSupport.generate(ap, isMulti);
            case CONDUCTOR:
                return generatorConductor.generate(ap, isMulti);
            case VIEW:
                return generatorView.generate(ap, isMulti);
            case DAGGER_ACTIVITY:
                return generatorDaggerActivity.generate(ap, isMulti);
            case DAGGER_FRAGMENT:
                return generatorDaggerFragment.generate(ap, isMulti);
            case DAGGER_FRAGMENT_SUPPORT:
                return generatorDaggerFragmentSupport.generate(ap, isMulti);
            case DAGGER_CONDUCTOR:
                return generatorDaggerConductor.generate(ap, isMulti);
            case DAGGER_VIEW:
                return generatorDaggerView.generate(ap, isMulti);
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
        List<Arg> args = null;

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

    private List<Arg> scanPresenterArgs(TypeElement typeElement) {
        final List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        for (Element enclosedElement : enclosedElements) {
            if (ElementKind.CONSTRUCTOR.equals(enclosedElement.getKind())) {
                // TODO: 2017-02-01 restrict to one constructor only
                final ExecutableElement constructor = (ExecutableElement) enclosedElement;
                List<? extends VariableElement> parameters = constructor.getParameters();
                List<Arg> args = new ArrayList<>(parameters.size());
                for (VariableElement parameter : parameters) {
                    final List<? extends AnnotationMirror> annotationMirrors =
                            parameter.getAnnotationMirrors();

                    final Arg presenterArg = new Arg(
                            parameter.getSimpleName().toString(),
                            parameter.asType(),
                            annotationMirrors);

                    args.add(presenterArg);
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
            error(typeElement, "@Presenter doesn't have the view class.");
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
                error(typeElement, "View class should extends Activity, Fragment, View or Conductor Controller");
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

}
