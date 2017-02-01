package com.github.slick;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

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
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import static com.squareup.javapoet.ClassName.get;

@AutoService(Processor.class)
public class SlickProcessor extends AbstractProcessor {

    private static final ClassName ClASS_NAME_ACTIVITY = get("android.app", "Activity");
    private static final ClassName CLASS_NAME_SLICK_DELEGATE = get("com.github.slick", "SlickDelegate");
    private static final ClassName ClASS_NAME_ON_DESTROY_LISTENER = get("com.github.slick", "OnDestroyListener");
    private static final ClassName ClASS_NAME_SLICK_VIEW = get("com.github.slick", "SlickView");

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
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
     * @param annotatedPresenter
     * @return TypeSpec
     */
    private TypeSpec generatePresenterHost(AnnotatedPresenter annotatedPresenter) {
        final ClassName view = annotatedPresenter.getView();
        final ClassName presenter = annotatedPresenter.getPresenter();
        final ClassName presenterHost = annotatedPresenter.getPresenterHost();
        final List<PresenterArgs> args = annotatedPresenter.getArgs();

        final TypeVariableName type = TypeVariableName.get("T", ClASS_NAME_ACTIVITY);
        final ParameterizedTypeName typeName =
                ParameterizedTypeName.get(CLASS_NAME_SLICK_DELEGATE, view, presenter);

        final FieldSpec delegate = FieldSpec.builder(typeName, "delegate")
                .initializer("new $T()", CLASS_NAME_SLICK_DELEGATE)
                .build();

        final String presenterInstanceName = "presenterInstance";
        final FieldSpec presenterInstance = FieldSpec.builder(presenter, presenterInstanceName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();
        final String hostInstanceName = "hostInstance";
        final FieldSpec hostInstance = FieldSpec.builder(presenterHost, hostInstanceName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();

        StringBuilder argsCode = new StringBuilder(args.size() * 10);
        if (args.size() > 0) {

            for (int i = 0; i < args.size() - 1; i++) {
                argsCode.append(args.get(i).getName()).append(", ");

            }
            argsCode.append(args.get(args.size() - 1).getName());
        }

        final String argActivityName = "activity";
        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(type.withBounds(ClASS_NAME_SLICK_VIEW))
                .addParameter(type, argActivityName)
                .addStatement("if ($L == null) $L = new $T()", hostInstanceName, hostInstanceName, presenterHost)
                .addStatement("if ($L == null) $L = new $T($L)", presenterInstanceName, presenterInstanceName,
                        presenter, argsCode.toString())
                .addStatement("return $L.setListener(activity)", hostInstanceName)
                .returns(presenter);

        for (PresenterArgs arg : args) {
            final ParameterSpec.Builder paramBuilder =
                    ParameterSpec.builder(TypeName.get(arg.getType()), arg.getName());
            for (AnnotationMirror annotationMirror : arg.getAnnotations()) {
                paramBuilder.addAnnotation(AnnotationSpec.get(annotationMirror));
            }
            methodBuilder.addParameter(paramBuilder.build());
        }

        final MethodSpec bind = methodBuilder.build();

        final MethodSpec setListener = MethodSpec.methodBuilder("setListener")
                .addModifiers(Modifier.PRIVATE)
                .addParameter(ClASS_NAME_ACTIVITY, argActivityName)
                .returns(presenter)
                .addStatement("$L.getApplication().registerActivityLifecycleCallbacks(delegate)",
                        argActivityName)
                .addStatement("delegate.onCreate($L)", presenterInstanceName)
                .addStatement("delegate.bind($L, $L.getClass())", presenterInstanceName, argActivityName)
                .addStatement("delegate.setListener(this)")
                .addStatement("return $L", presenterInstanceName)
                .build();

        final MethodSpec onDestroy = MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$L = null", presenterInstanceName)
                .addStatement("$L = null", hostInstanceName)
                .build();


        return TypeSpec.classBuilder(presenterHost)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClASS_NAME_ON_DESTROY_LISTENER)
                .addField(delegate)
                .addField(presenterInstance)
                .addField(hostInstance)
                .addMethod(bind)
                .addMethod(setListener)
                .addMethod(onDestroy)
                .build();
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
                return new AnnotatedPresenter(typeArguments.get(0).toString(), args, presenter, presenterHost);
            }
        }
        throw new IllegalStateException("Could not scan presenter");
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
