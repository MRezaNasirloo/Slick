package com.github.slick.middleware;

import com.github.slick.Middleware;
import com.github.slick.SlickVisitor;
import com.github.slick.middleware.components.MiddlewareGenerator;
import com.github.slick.middleware.components.MiddlewareGeneratorBaseImpl;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-15
 */

@AutoService(Processor.class)
public class MiddlewareProcessor extends AbstractProcessor {

    //RxJava2 types
    static final ClassName CLASS_NAME_COMPLETEBLE = ClassName.get("io.reactivex", "Completable");
    static final ClassName CLASS_NAME_OBSERVABLE = ClassName.get("io.reactivex", "Observable");
    static final ClassName CLASS_NAME_FLOWABLE = ClassName.get("io.reactivex", "Flowable");
    static final ClassName CLASS_NAME_SINGLE = ClassName.get("io.reactivex", "Single");
    static final ClassName CLASS_NAME_MAYBE = ClassName.get("io.reactivex", "Maybe");

    //RxJava2 bridge types
    static final ClassName CLASS_NAME_COMPLETABLE_SUBJECT =
            ClassName.get("io.reactivex.subjects", "CompletableSubject");
    static final ClassName CLASS_NAME_PUBLISH_SUBJECT = ClassName.get("io.reactivex.subjects", "PublishSubject");
    static final ClassName CLASS_NAME_PUBLISH_PROCESSOR =
            ClassName.get("io.reactivex.processors", "PublishProcessor");
    static final ClassName CLASS_NAME_SINGLE_SUBJECT = ClassName.get("io.reactivex.subjects", "SingleSubject");
    static final ClassName CLASS_NAME_MAYBE_SUBJECT = ClassName.get("io.reactivex.subjects", "MaybeSubject");

    //Slick Request types
    static final ClassName CLASS_NAME_REQUEST_COMPLETEBLE =
            ClassName.get("com.github.slick.middlewares", "RequestCompletable");
    static final ClassName CLASS_NAME_REQUEST_OBSERVABLE =
            ClassName.get("com.github.slick.middlewares", "RequestObservable");
    static final ClassName CLASS_NAME_REQUEST_FLOWABLE =
            ClassName.get("com.github.slick.middlewares", "RequestFlowable");
    static final ClassName CLASS_NAME_REQUEST_SINGLE =
            ClassName.get("com.github.slick.middlewares", "RequestSingle");
    static final ClassName CLASS_NAME_REQUEST_MAYBE =
            ClassName.get("com.github.slick.middlewares", "RequestMaybe");
    static final ClassName CLASS_NAME_REQUEST_SIMPLE =
            ClassName.get("com.github.slick.middlewares", "RequestSimple");

    //Slick Middleware's classes
    static final ClassName CLASS_NAME_REQUEST_STACK =
            ClassName.get("com.github.slick.middlewares", "RequestStack");
    static final ClassName CLASS_NAME_CALLBACK = ClassName.get("com.github.slick.middlewares", "Callback");

    private static final MiddlewareGenerator generator = new MiddlewareGeneratorBaseImpl();

    private Filer filer;
    private Messager messager;
    public static Types typeUtils;

    public enum MethodType {
        COMPLETABLE(CLASS_NAME_COMPLETABLE_SUBJECT, CLASS_NAME_REQUEST_COMPLETEBLE),
        OBSERVABLE(CLASS_NAME_PUBLISH_SUBJECT, CLASS_NAME_REQUEST_OBSERVABLE),
        FLOWABLE(CLASS_NAME_PUBLISH_PROCESSOR, CLASS_NAME_REQUEST_FLOWABLE),
        SINGLE(CLASS_NAME_SINGLE_SUBJECT, CLASS_NAME_REQUEST_SINGLE),
        MAYBE(CLASS_NAME_MAYBE_SUBJECT, CLASS_NAME_REQUEST_MAYBE),
        SIMPLE(null, CLASS_NAME_REQUEST_SIMPLE),
        UNSUPPORTED(null, null);

        public final ClassName bridgeType;
        public final ClassName requestType;

        MethodType(ClassName bridgeType, ClassName requestType) {
            this.bridgeType = bridgeType;
            this.requestType = requestType;
        }
    }

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
        supportedTypes.add(Middleware.class.getCanonicalName());
        return supportedTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        final Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Middleware.class);
        final Map<ContainerClass, List<AnnotatedMethod>> map = scan(elements);
        for (ContainerClass container : map.keySet()) {
            brewJava(container.getClassName().packageName(), generator.generate(container, map.get(container)));
        }
        return true;
    }

    protected Map<ContainerClass, List<AnnotatedMethod>> scan(Set<? extends Element> elements) {
        Map<ContainerClass, List<AnnotatedMethod>> map = new HashMap<>();
        List<AnnotatedMethod> annotatedMethods;
        for (Element element : elements) {
            ContainerClass container = null;
            final TypeElement containerClass = ((TypeElement) element.getEnclosingElement());


            final List<? extends Element> enclosedElements = containerClass.getEnclosedElements();
            final List<? extends TypeParameterElement> typeParameters = containerClass.getTypeParameters();
            List<? extends VariableElement> constrictorParameters;
            for (Element enclosedElement : enclosedElements) {
                if (ElementKind.CONSTRUCTOR.equals(enclosedElement.getKind())) {
                    final ExecutableElement constructor = (ExecutableElement) enclosedElement;
                    constrictorParameters = constructor.getParameters();
                    container = new ContainerClass(TypeName.get(containerClass.asType()),
                            ClassName.get(containerClass), constrictorParameters, typeParameters);
                    break;
                }
            }
            if (map.containsKey(container)) {
                annotatedMethods = map.get(container);
            } else {
                annotatedMethods = new ArrayList<>();
                map.put(container, annotatedMethods);
            }
            for (Element enclosedElement : enclosedElements) {
                if (ElementKind.METHOD.equals(enclosedElement.getKind()) &&
                        null != enclosedElement.getAnnotation(Middleware.class)) {
                    final List<? extends AnnotationMirror> annotationMirrors =
                            enclosedElement.getAnnotationMirrors();
                    final ExecutableElement executableElement = (ExecutableElement) enclosedElement;


                    final TypeName typeName = ClassName.get(executableElement.getReturnType());
                    final TypeName returnTypeName = typeName instanceof ParameterizedTypeName ?
                            ((ParameterizedTypeName) typeName).rawType : typeName;

                    MethodType type;
                    if (returnTypeName.equals(CLASS_NAME_OBSERVABLE)) {
                        type = MethodType.OBSERVABLE;
                    } else if (returnTypeName.equals(CLASS_NAME_FLOWABLE)) {
                        type = MethodType.FLOWABLE;
                    } else if (returnTypeName.equals(CLASS_NAME_MAYBE)) {
                        type = MethodType.MAYBE;
                    } else if (returnTypeName.equals(CLASS_NAME_SINGLE)) {
                        type = MethodType.SINGLE;
                    } else if (returnTypeName.equals(CLASS_NAME_COMPLETEBLE)) {
                        type = MethodType.COMPLETABLE;
                    } else {
                        type = MethodType.SIMPLE;
                    }

                    final AnnotatedMethod annotatedMethod =
                            new AnnotatedMethod(executableElement,type, container,
                                    enclosedElement.getSimpleName().toString(), executableElement.getParameters(),
                                    getMiddlewareList(annotationMirrors), typeName);
                    annotatedMethods.add(annotatedMethod);
                }
            }
        }
        return map;
    }

    private List<TypeMirror> getMiddlewareList(List<? extends AnnotationMirror> annotationMirrors) {
        final ArrayList<TypeMirror> list = new ArrayList<>();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror
                    .getElementValues()
                    .entrySet()) {
                entry.getValue().accept(new SlickVisitor(), list);
            }
        }
        return list;
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
}
