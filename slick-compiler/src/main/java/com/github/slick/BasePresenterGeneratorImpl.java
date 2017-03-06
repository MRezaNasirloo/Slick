package com.github.slick;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.ClASS_NAME_HASH_MAP;
import static com.github.slick.SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
class BasePresenterGeneratorImpl implements PresenterGenerator {

    private final com.github.slick.components.MethodSignatureGenerator methodSignatureGenerator;
    private final com.github.slick.components.BindMethodBodyGenerator methodBodyGenerator;
    private com.github.slick.components.AddMethodGenerator addMethodGenerator;

    public BasePresenterGeneratorImpl(com.github.slick.components.MethodSignatureGenerator methodSignatureGenerator,
                                      com.github.slick.components.BindMethodBodyGenerator methodBodyGenerator) {
        this.methodSignatureGenerator = methodSignatureGenerator;
        this.methodBodyGenerator = methodBodyGenerator;
    }

    public BasePresenterGeneratorImpl(com.github.slick.components.MethodSignatureGenerator methodSignatureGenerator,
                                      com.github.slick.components.BindMethodBodyGenerator methodBodyGenerator,
                                      com.github.slick.components.AddMethodGenerator addMethodGenerator) {
        this.methodSignatureGenerator = methodSignatureGenerator;
        this.methodBodyGenerator = methodBodyGenerator;
        this.addMethodGenerator = addMethodGenerator;
    }

    protected String varNameDelegate = "delegate";
    protected String fieldNameDelegates = "delegates";
    protected String hostInstanceName = "hostInstance";
    protected String presenterName = "presenter";

    @Override
    public TypeSpec generate(AnnotatedPresenter ap) {
        final FieldSpec hostInstance = FieldSpec.builder(ap.getPresenterHost(), hostInstanceName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();

        final MethodSpec.Builder bindMethodSignature =
                methodSignatureGenerator.generate("bind", ap, returnType(ap));

        final MethodSpec.Builder methodBuilder = methodBodyGenerator.generate(bindMethodSignature, ap);

        return TypeSpec.classBuilder(ap.getPresenterHost())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClASS_NAME_ON_DESTROY_LISTENER)
                .addField(getDelegateField(ap))
                .addField(hostInstance)
                .addMethod(methodBuilder.build())
                .addMethods(addMethods(ap))
                .addMethod(onDestroyMethod(ap))
                .build();
    }

    protected TypeName returnType(AnnotatedPresenter ap) {
        return TypeName.get(void.class);
    }

    public MethodSpec onDestroyMethod(AnnotatedPresenter ap) {
        return MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClASS_NAME_STRING, "id").build())
                .addStatement("$L.$L.remove(id)", hostInstanceName, fieldNameDelegates)
                .beginControlFlow("if ($L.$L.size() == 0)", hostInstanceName, fieldNameDelegates)
                .addStatement("$L = null", hostInstanceName)
                .endControlFlow()
                .build();
    }

    /**
     * Builds the bind method
     *
     * @param builder
     * @param ap
     * @param view            the class which implements the view interface
     * @param presenter       presenter class
     * @param presenterHost   presenter host class
     * @param fieldName       presenter name in view class
     * @param viewGenericType activity type
     * @param argsCode        the presenter parameters in a comma separated string    @return bind method builder
     */
    /*protected abstract MethodSpec.Builder bindMethodBody(MethodSpec.Builder builder, AnnotatedPresenter ap,
                                                         ClassName view,
                                                         ClassName presenter,
                                                         ClassName presenterHost,
                                                         ClassName classNameDelegate,
                                                         String fieldName,
                                                         TypeVariableName viewGenericType,
                                                         String argsCode);*/


    /**
     * @param ap
     * @return delegate field
     */
    protected FieldSpec getDelegateField(AnnotatedPresenter ap) {
        final ParameterizedTypeName parametrizedMapTypeName =
                ParameterizedTypeName.get(ClASS_NAME_HASH_MAP, ClASS_NAME_STRING,
                        ap.getDelegateParametrizedType());

        return FieldSpec.builder(parametrizedMapTypeName, fieldNameDelegates)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T<>()", ClASS_NAME_HASH_MAP)
                .build();
    }

    private Iterable<MethodSpec> addMethods(AnnotatedPresenter ap) {
        if (addMethodGenerator != null) {
            return addMethodGenerator.generate(ap);
        }
        return new ArrayList<>(0);
    }

}
