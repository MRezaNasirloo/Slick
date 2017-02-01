package com.github.slick;

import com.github.Presenter;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Processor.class)
public class SlickProcessor extends AbstractProcessor {

    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        this.processingEnv = processingEnv;

        this.elementUtils = processingEnv.getElementUtils();
        this.typeUtils = processingEnv.getTypeUtils();
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

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final SlickVisitor slickVisitor = new SlickVisitor();

        for (Element element : roundEnv.getElementsAnnotatedWith(Presenter.class)) {
            AnnotatedPresenter annotatedPresenter = null;
            final TypeElement typeElement = (TypeElement) element;
            final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror
                        .getElementValues()
                        .entrySet()) {
                    annotatedPresenter = entry.getValue().accept(slickVisitor, null);
                }
            }
            // TODO: 2017-02-01 get the view name from super class parametrized type
            // TODO: 2017-02-01 refactor the code generating part to its own class
            // TODO: 2017-02-01 fill the annotatedPresenter with more data
            // TODO: 2017-02-01 generate the constructors args
            // TODO: 2017-02-01 generate dagger delegate
            // TODO: 2017-02-01 generate host for fragment's presenter
            if (annotatedPresenter == null) {
                // TODO: 2017-02-01 error
                continue;
            }
            System.out.println("process() called with: hey" + element.asType().toString());
            final ClassName activity = ClassName.get("android.app", "Activity");
            final ClassName presenter = ClassName.get(
                    element.asType().toString().replace("." + element.getSimpleName().toString(), ""),
                    element.getSimpleName().toString());
            final ClassName presenterHost =
                    ClassName.get(presenter.packageName(), element.getSimpleName().toString() + "_HOST");

            final TypeVariableName type = TypeVariableName.get("T", activity);
            final ClassName slickDelegate = ClassName.get("com.github.slick", "SlickDelegate");
            final ClassName onDestroyListener = ClassName.get("com.github.slick", "OnDestroyListener");
            final ClassName slickView = ClassName.get("com.github.slick", "SlickView");
            final ClassName view = annotatedPresenter.getClassName();
            final ParameterizedTypeName typeName = ParameterizedTypeName.get(slickDelegate, view, presenter);

            final FieldSpec delegate = FieldSpec.builder(typeName, "delegate")
                    .initializer("new $T()", slickDelegate)
                    .build();

            final String presenterInstanceName = "presenterInstance";
            final FieldSpec presenterInstance = FieldSpec.builder(presenter, presenterInstanceName)
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .build();
            final String hostInstanceName = "hostInstance";
            final FieldSpec hostInstance = FieldSpec.builder(presenterHost, hostInstanceName)
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                    .build();

            // TODO: 2017-02-01 add presenter constructors args
            final String argActivityName = "activity";
            final MethodSpec bind = MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addTypeVariable(type.withBounds(slickView))
                    .addParameter(type, argActivityName)
                    .addStatement("if ($L == null) $L = new $T()",hostInstanceName, hostInstanceName, presenterHost)
                    .addStatement("if ($L == null) $L = new $T()",presenterInstanceName, presenterInstanceName, presenter)
                    .addStatement("return $L.setListener(activity)", hostInstanceName)
                    .returns(presenter).build();

            final MethodSpec setListener = MethodSpec.methodBuilder("setListener")
                    .addModifiers(Modifier.PRIVATE)
                    .addParameter(activity, argActivityName)
                    .returns(presenter)
                    .addStatement("$L.getApplication().registerActivityLifecycleCallbacks(delegate)", argActivityName)
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


            final TypeSpec cls = TypeSpec.classBuilder(presenterHost)
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(onDestroyListener)
                    .addField(delegate)
                    .addField(presenterInstance)
                    .addField(hostInstance)
                    .addMethod(bind)
                    .addMethod(setListener)
                    .addMethod(onDestroy)
                    .build();

            final JavaFile javaFile = JavaFile.builder(presenter.packageName(), cls).build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
