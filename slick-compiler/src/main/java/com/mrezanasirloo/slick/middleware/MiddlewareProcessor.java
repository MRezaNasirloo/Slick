package com.mrezanasirloo.slick.middleware;

import com.google.auto.service.AutoService;
import com.mrezanasirloo.slick.Middleware;
import com.mrezanasirloo.slick.SlickVisitor;
import com.mrezanasirloo.slick.middleware.components.ConstructorGenerator;
import com.mrezanasirloo.slick.middleware.components.ConstructorGeneratorImpl;
import com.mrezanasirloo.slick.middleware.components.FieldGenerator;
import com.mrezanasirloo.slick.middleware.components.FieldGeneratorImpl;
import com.mrezanasirloo.slick.middleware.components.MiddlewareGenerator;
import com.mrezanasirloo.slick.middleware.components.MiddlewareGeneratorBaseImpl;
import com.mrezanasirloo.slick.middleware.components.RequestTypeGenerator;
import com.mrezanasirloo.slick.middleware.components.RequestTypeGenerator1ArgsImpl;
import com.mrezanasirloo.slick.middleware.components.RequestTypeGenerator2ArgsImpl;
import com.mrezanasirloo.slick.middleware.components.RequestTypeGenerator3ArgsImpl;
import com.mrezanasirloo.slick.middleware.components.RxSourceGenerator;
import com.mrezanasirloo.slick.middleware.components.RxSourceGeneratorCompletableImpl;
import com.mrezanasirloo.slick.middleware.components.RxSourceGeneratorImpl;
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
import javax.tools.Diagnostic;

import static com.squareup.javapoet.ClassName.get;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-15
 */

@AutoService(Processor.class)
public class MiddlewareProcessor extends AbstractProcessor {

    private static final String packageName = "com.mrezanasirloo.slick.middleware";
    //RxJava2 types
    public static final ClassName CLASS_NAME_COMPLETABLE = get("io.reactivex", "Completable");
    public static final ClassName CLASS_NAME_OBSERVABLE = get("io.reactivex", "Observable");
    public static final ClassName CLASS_NAME_FLOWABLE = get("io.reactivex", "Flowable");
    public static final ClassName CLASS_NAME_SINGLE = get("io.reactivex", "Single");
    public static final ClassName CLASS_NAME_MAYBE = get("io.reactivex", "Maybe");

    //RxJava2 bridge types
    public static final ClassName CLASS_NAME_COMPLETABLE_SUBJECT = get("io.reactivex.subjects", "CompletableSubject");
    public static final ClassName CLASS_NAME_REPLAY_SUBJECT = get("io.reactivex.subjects", "ReplaySubject");
    public static final ClassName CLASS_NAME_REPLAY_PROCESSOR = get("io.reactivex.processors", "ReplayProcessor");
    public static final ClassName CLASS_NAME_SINGLE_SUBJECT = get("io.reactivex.subjects", "SingleSubject");
    public static final ClassName CLASS_NAME_MAYBE_SUBJECT = get("io.reactivex.subjects", "MaybeSubject");

    //Slick Request types
    public static final ClassName CLASS_NAME_REQUEST_COMPLETABLE = get(packageName + ".rx2", "RequestCompletable");
    public static final ClassName CLASS_NAME_REQUEST_OBSERVABLE = get(packageName + ".rx2", "RequestObservable");
    public static final ClassName CLASS_NAME_REQUEST_FLOWABLE = get(packageName + ".rx2", "RequestFlowable");
    public static final ClassName CLASS_NAME_REQUEST_SINGLE = get(packageName + ".rx2", "RequestSingle");
    public static final ClassName CLASS_NAME_REQUEST_MAYBE = get(packageName + ".rx2", "RequestMaybe");
    public static final ClassName CLASS_NAME_REQUEST_SIMPLE = get(packageName, "RequestSimple");

    //Slick Middleware's classes
    public static final ClassName CLASS_NAME_REQUEST_STACK = get(packageName, "RequestStack");
    public static final ClassName CLASS_NAME_CALLBACK = get(packageName, "Callback");

    private final ConstructorGenerator consGenerator = new ConstructorGeneratorImpl();
    private final FieldGenerator fieldGenerator = new FieldGeneratorImpl();
    private final MiddlewareGenerator generator = new MiddlewareGeneratorBaseImpl(consGenerator, fieldGenerator);

    private static final RequestTypeGenerator REQUEST_TYPE_GENERATOR_1_ARGS = new RequestTypeGenerator1ArgsImpl();
    private static final RequestTypeGenerator REQUEST_TYPE_GENERATOR_2_ARGS = new RequestTypeGenerator2ArgsImpl();
    private static final RequestTypeGenerator REQUEST_TYPE_GENERATOR_3_ARGS = new RequestTypeGenerator3ArgsImpl();
    private static final RxSourceGenerator RX_SOURCE_GENERATOR = new RxSourceGeneratorImpl();
    private static final RxSourceGenerator RX_SOURCE_GENERATOR_COMPLETABLE = new RxSourceGeneratorCompletableImpl();

    public enum MethodType {
        COMPLETABLE(CLASS_NAME_COMPLETABLE_SUBJECT, CLASS_NAME_REQUEST_COMPLETABLE, RX_SOURCE_GENERATOR_COMPLETABLE, REQUEST_TYPE_GENERATOR_1_ARGS),
        OBSERVABLE(CLASS_NAME_REPLAY_SUBJECT, CLASS_NAME_REQUEST_OBSERVABLE, RX_SOURCE_GENERATOR, REQUEST_TYPE_GENERATOR_3_ARGS),
        FLOWABLE(CLASS_NAME_REPLAY_PROCESSOR, CLASS_NAME_REQUEST_FLOWABLE, RX_SOURCE_GENERATOR, REQUEST_TYPE_GENERATOR_3_ARGS),
        SINGLE(CLASS_NAME_SINGLE_SUBJECT, CLASS_NAME_REQUEST_SINGLE, RX_SOURCE_GENERATOR, REQUEST_TYPE_GENERATOR_3_ARGS),
        MAYBE(CLASS_NAME_MAYBE_SUBJECT, CLASS_NAME_REQUEST_MAYBE, RX_SOURCE_GENERATOR, REQUEST_TYPE_GENERATOR_3_ARGS),
        SIMPLE(null, CLASS_NAME_REQUEST_SIMPLE, RX_SOURCE_GENERATOR, REQUEST_TYPE_GENERATOR_2_ARGS),
        UNSUPPORTED(null, null, null, null);

        public final ClassName bridgeType;
        public final ClassName requestType;
        public final RxSourceGenerator rxSourceGenerator;
        public final RequestTypeGenerator requestTypeGenerator;

        MethodType(ClassName bridgeType, ClassName requestType, RxSourceGenerator rxSourceGenerator,
                   RequestTypeGenerator requestTypeGenerator) {
            this.bridgeType = bridgeType;
            this.requestType = requestType;
            this.rxSourceGenerator = rxSourceGenerator;
            this.requestTypeGenerator = requestTypeGenerator;
        }
    }

    private Filer filer;
    private Messager messager;
    static Types typeUtils;

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

    private Map<ContainerClass, List<AnnotatedMethod>> scan(Set<? extends Element> elements) {
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
                            get(containerClass), constrictorParameters, typeParameters, constructor.getAnnotationMirrors());
                    break;
                }
            }
            if (map.containsKey(container)) {
                // already processed
                continue;
            } else {
                annotatedMethods = new ArrayList<>();
                map.put(container, annotatedMethods);
            }
            for (Element enclosedElement : enclosedElements) {
                if (ElementKind.METHOD.equals(enclosedElement.getKind()) &&
                        null != enclosedElement.getAnnotation(Middleware.class)) {
                    final List<? extends AnnotationMirror> annotationMirrors = enclosedElement.getAnnotationMirrors();
                    final ExecutableElement executableElement = (ExecutableElement) enclosedElement;


                    final TypeName typeName = get(executableElement.getReturnType());
                    final TypeName returnTypeName = typeName instanceof ParameterizedTypeName ?
                            ((ParameterizedTypeName) typeName).rawType : typeName;

                    if (typeName.isPrimitive()) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "primitive returns types are not supported.",
                                element);
                    }

                    MethodType type;
                    if (returnTypeName.equals(CLASS_NAME_OBSERVABLE)) {
                        type = MethodType.OBSERVABLE;
                    } else if (returnTypeName.equals(CLASS_NAME_FLOWABLE)) {
                        type = MethodType.FLOWABLE;
                    } else if (returnTypeName.equals(CLASS_NAME_MAYBE)) {
                        type = MethodType.MAYBE;
                    } else if (returnTypeName.equals(CLASS_NAME_SINGLE)) {
                        type = MethodType.SINGLE;
                    } else if (returnTypeName.equals(CLASS_NAME_COMPLETABLE)) {
                        type = MethodType.COMPLETABLE;
                    } else {
                        type = MethodType.SIMPLE;
                    }
                    if (!(typeName instanceof ParameterizedTypeName) && type != MethodType.COMPLETABLE) {
                        type = MethodType.SIMPLE;
                    }

                    final List<? extends VariableElement> parameters = executableElement.getParameters();
                    if (parameters.size() > 2 || is2ndArgNotOk(parameters, typeName, element)) {
                        messager.printMessage(Diagnostic.Kind.ERROR,
                                "Only 0-2 parameters are allowed, first one could be any object type or a BundleSlick," +
                                        " the second one could be a Callback<methodReturnType>.", element);
                    }


                    final AnnotatedMethod annotatedMethod =
                            new AnnotatedMethod(executableElement, type, container,
                                    enclosedElement.getSimpleName().toString(), parameters,
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

    private boolean is2ndArgNotOk(List<? extends VariableElement> parameters, TypeName typeName, Element element) {
        if (parameters.size() == 2) {
            final TypeName callback = get(parameters.get(1).asType());
            if (callback instanceof ParameterizedTypeName) {
                final List<TypeName> typeArguments = ((ParameterizedTypeName) callback).typeArguments;
                if (typeArguments.size() != 1) {
                    return true;
                }
                if (!typeArguments.get(0).equals(typeName)) {
                    messager.printMessage(Diagnostic.Kind.NOTE,
                            "Callback parametrized type should be same as the method return type.", element);
                    return true;
                }
            }
            return !(callback instanceof ParameterizedTypeName &&
                    ((ParameterizedTypeName) callback).rawType.equals(CLASS_NAME_CALLBACK));
        }
        return false;
    }
}
