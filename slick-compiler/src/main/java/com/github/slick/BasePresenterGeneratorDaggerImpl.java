package com.github.slick;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_DELEGATE;
import static com.github.slick.SlickProcessor.ClASS_NAME_HASH_MAP;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
abstract class BasePresenterGeneratorDaggerImpl extends BasePresenterGeneratorImpl {

    public BasePresenterGeneratorDaggerImpl(MethodSignatureGenerator generator) {
        super(generator);
    }

    @Override
    protected MethodSpec onDestroyMethod() {
        return MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClASS_NAME_STRING, "id").build())
                .addStatement("$L = null", hostInstanceName)
                .build();
    }

    @Override
    protected FieldSpec getDelegateField(ParameterizedTypeName typeNameDelegate) {
        return FieldSpec.builder(typeNameDelegate, varNameDelegate)
                .build();
    }
}
