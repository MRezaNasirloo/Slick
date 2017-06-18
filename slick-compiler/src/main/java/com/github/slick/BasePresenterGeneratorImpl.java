package com.github.slick;

import com.github.slick.components.AddMethodGenerator;
import com.github.slick.components.BindMethodBodyGenerator;
import com.github.slick.components.MethodSignatureGenerator;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;

import static com.github.slick.AnnotatedPresenter.DELEGATES_FIELD_NAME;
import static com.github.slick.AnnotatedPresenter.HOST_INSTANCE_VAR_NAME;
import static com.github.slick.SlickProcessor.ClASS_NAME_HASH_MAP;
import static com.github.slick.SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
class BasePresenterGeneratorImpl implements PresenterGenerator {

    private final MethodSignatureGenerator methodSignatureGenerator;
    private final BindMethodBodyGenerator methodBodyGenerator;
    private AddMethodGenerator addMethodGenerator;

    public BasePresenterGeneratorImpl(MethodSignatureGenerator methodSignatureGenerator,
                                      BindMethodBodyGenerator methodBodyGenerator) {
        this.methodSignatureGenerator = methodSignatureGenerator;
        this.methodBodyGenerator = methodBodyGenerator;
    }

    public BasePresenterGeneratorImpl(MethodSignatureGenerator methodSignatureGenerator,
                                      BindMethodBodyGenerator methodBodyGenerator,
                                      AddMethodGenerator addMethodGenerator) {
        this.methodSignatureGenerator = methodSignatureGenerator;
        this.methodBodyGenerator = methodBodyGenerator;
        this.addMethodGenerator = addMethodGenerator;
    }

    @Override
    public TypeSpec generate(AnnotatedPresenter ap) {
        final FieldSpec hostInstance = FieldSpec.builder(ap.getPresenterHost(), HOST_INSTANCE_VAR_NAME)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();

        final MethodSpec.Builder bindMethodSignature =
                methodSignatureGenerator.generate("bind", ap, TypeName.get(void.class));

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

    /**
     * @param ap annotated presenter object
     * @return method spec
     */
    public MethodSpec onDestroyMethod(AnnotatedPresenter ap) {
        return MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(int.class, "id").build())
                .addStatement("$L.$L.remove(id)", HOST_INSTANCE_VAR_NAME, DELEGATES_FIELD_NAME)
                .beginControlFlow("if ($L.$L.size() == 0)", HOST_INSTANCE_VAR_NAME, DELEGATES_FIELD_NAME)
                .addStatement("$L = null", HOST_INSTANCE_VAR_NAME)
                .endControlFlow()
                .build();
    }

    /**
     * @param ap annotated presenter object
     * @return delegate field
     */
    private FieldSpec getDelegateField(AnnotatedPresenter ap) {
        final ParameterizedTypeName parametrizedMapTypeName =
                ParameterizedTypeName.get(ClASS_NAME_HASH_MAP, ap.getParametrizedDelegateType());

        return FieldSpec.builder(parametrizedMapTypeName, DELEGATES_FIELD_NAME)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T<>()", ClASS_NAME_HASH_MAP)
                .build();
    }

    /**
     * Utility to add extra arbitrary methods
     *
     * @param ap annotated presenter object
     * @return a list of method specs
     */
    private Iterable<MethodSpec> addMethods(AnnotatedPresenter ap) {
        if (addMethodGenerator != null) {
            return addMethodGenerator.generate(ap);
        }
        return new ArrayList<>(0);
    }

}
