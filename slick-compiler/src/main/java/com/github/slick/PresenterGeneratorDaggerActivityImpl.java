package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_DELEGATE;
import static com.github.slick.SlickProcessor.ClASS_NAME_ACTIVITY;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
class PresenterGeneratorDaggerActivityImpl extends BasePresenterGeneratorDaggerImpl {


    public PresenterGeneratorDaggerActivityImpl(MethodSignatureGenerator generator) {
        super(generator);
    }

    @Override
    protected MethodSpec.Builder bindMethodBody(MethodSpec.Builder builder, AnnotatedPresenter ap, ClassName view,
                                                ClassName presenter,
                                                ClassName presenterHost,
                                                ClassName classNameDelegate,
                                                String fieldName, String argNameView,
                                                String presenterArgName, TypeVariableName viewGenericType,
                                                ParameterizedTypeName typeNameDelegate, String argsCode) {
        return builder
                .beginControlFlow("if ($L == null)", hostInstanceName)
                .addStatement("$L = new $T()", hostInstanceName, presenterHost)
                .addStatement("$T presenter = (($T) $L).$L", presenter, view, argNameView, fieldName)
                .addStatement("$L.$L = new $T<>($L, $L.getClass())", hostInstanceName, varNameDelegate,
                        classNameDelegate, presenterName, argNameView)
                .addStatement("$L.getApplication().registerActivityLifecycleCallbacks($L.$L)", argNameView,
                        hostInstanceName, varNameDelegate)
                .addStatement("$L.$L.setListener($L)", hostInstanceName, varNameDelegate, hostInstanceName)
                .endControlFlow()
                .returns(void.class);
    }
}
