package com.github.slick;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
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
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.ClassName.get;

@AutoService(Processor.class)
public class SlickProcessor extends AbstractProcessor {

    static final String ACTIVITY = "android.app.Activity";
    static final String FRAGMENT = "android.app.Fragment";
    static final String FRAGMENT_SUPPORT = "android.support.v4.app.Fragment";
    static final String VIEW = "android.view.View";
    static final ClassName ClASS_NAME_ACTIVITY = get("android.app", "Activity");
    static final ClassName ClASS_NAME_FRAGMENT = get("android.app", "Fragment");
    static final ClassName ClASS_NAME_FRAGMENT_SUPPORT = get("android.support.v4.app", "Fragment");
    static final ClassName ClASS_NAME_VIEW = get("android.view", "View");
    static final ClassName CLASS_NAME_SLICK_DELEGATOR = get("com.github.slick", "SlickDelegator");
    static final ClassName CLASS_NAME_SLICK_DELEGATE = get("com.github.slick", "SlickDelegate");
    static final ClassName ClASS_NAME_ON_DESTROY_LISTENER = get("com.github.slick", "OnDestroyListener");
    static final ClassName ClASS_NAME_SLICK_VIEW = get("com.github.slick", "SlickView");
    static final SlickVisitor SLICK_VISITOR = new SlickVisitor();

    private Filer filer;
    private Messager messager;
    private Types typeUtils;
    private PresenterGenerator generatorActivity;
    private PresenterGenerator generatorFragment;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
        typeUtils = processingEnvironment.getTypeUtils();
        generatorActivity = new PresenterGeneratorActivityImpl();
        generatorFragment = new PresenterGeneratorFragmentImpl();
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
    // TODO: 2017-02-01 generate dagger delegate
    // TODO: 2017-02-01 generate host for fragment's presenter
    // TODO: 2017-02-01 refactor the code generating part to its own class
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Presenter.class)) {
            final TypeElement typeElement = (TypeElement) element;
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

            final AnnotatedPresenter annotatedPresenter;
            try {
                annotatedPresenter = scanPresenter(typeElement, typeArguments);
            } catch (Exception e) {
                logParsingError(element, Presenter.class, e);
                continue;
            }

            final TypeSpec cls = generatePresenterHost(annotatedPresenter);

            final JavaFile javaFile =
                    JavaFile.builder(annotatedPresenter.getPresenter().packageName(), cls).build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Generates the Presenter Host class from provided information
     *
     * @param ap
     * @return TypeSpec
     */
    private TypeSpec generatePresenterHost(AnnotatedPresenter ap) {
        switch (ap.getViewType().toString()) {
            case ACTIVITY:
                return generatorActivity.generate(ap);
            case FRAGMENT:
            case FRAGMENT_SUPPORT:
                return generatorFragment.generate(ap);
            default: throw new IllegalStateException();
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
    private AnnotatedPresenter scanPresenter(TypeElement typeElement, List<? extends TypeMirror> typeArguments)
            throws IllegalArgumentException, IllegalStateException {
        final ClassName presenter = get(
                typeElement.asType().toString().replace("." + typeElement.getSimpleName().toString(), ""),
                typeElement.getSimpleName().toString());
        final ClassName presenterHost = get(presenter.packageName(),
                typeElement.getSimpleName().toString() + "_HOST");

        final TypeMirror viewTypeMirror = getViewTypeMirror(typeElement);
        final TypeElement viewType = getViewType(typeElement, viewTypeMirror);

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
                    final PresenterArgs presenterArgs = new PresenterArgs(parameter.getSimpleName().toString(),
                            parameter.asType(), annotationMirrors);

                    args.add(presenterArgs);
                }
                return new AnnotatedPresenter(typeArguments.get(0).toString(), args, get(
                        (TypeElement) typeUtils.asElement(viewTypeMirror)), get(viewType), presenter,
                        presenterHost);
            }
        }
        throw new IllegalStateException("Could not scan presenter");
    }

    private TypeElement getViewType(TypeElement typeElement, TypeMirror viewType) throws IllegalArgumentException {
        TypeElement viewTypeElement = (TypeElement) typeUtils.asElement(viewType);
        if (viewType == null) {
            error(typeElement, "@Presenter doesn't have the view class. @Presenter(YourActivityOrFragment.class)");
            throw new IllegalArgumentException("error");
        }
        while (true) {
            if (ClASS_NAME_ACTIVITY.toString().equals(viewTypeElement.toString()) ||
                    ClASS_NAME_FRAGMENT.toString().equals(viewTypeElement.toString()) ||
                    ClASS_NAME_FRAGMENT_SUPPORT.toString().equals(viewTypeElement.toString()) ||
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

    private TypeMirror getViewTypeMirror(TypeElement typeElement) {
        final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror
                    .getElementValues()
                    .entrySet()) {
                return entry.getValue().accept(SLICK_VISITOR, null);
            }
        }
        return null;
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
