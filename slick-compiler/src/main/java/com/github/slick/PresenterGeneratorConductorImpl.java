package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.CLASS_NAME_CONDUCTOR;
import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_CONDUCTOR_DELEGATE;
import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_DELEGATE;
import static com.github.slick.SlickProcessor.ClASS_NAME_ACTIVITY;
import static com.github.slick.SlickProcessor.ClASS_NAME_SLICK_VIEW;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
public class PresenterGeneratorConductorImpl extends BasePresenterGeneratorActivityImpl
        implements PresenterGenerator {
    @Override
    protected MethodSpec.Builder bindMethod(ClassName view, ClassName presenter, ClassName presenterHost,
                                            String fieldName, String argNameView,
                                            TypeVariableName activityGenericType,
                                            ParameterizedTypeName typeNameDelegate, StringBuilder argsCode) {
        return MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(activityGenericType.withBounds(ClASS_NAME_SLICK_VIEW))
                .addParameter(activityGenericType, argNameView)
                .addStatement("final String id = $L.getInstanceId()", argNameView)
                .addStatement("if ($L == null) $L = new $T()", hostInstanceName, hostInstanceName, presenterHost)
                .addStatement("$T $L = $L.$L.get(id)", typeNameDelegate, varNameDelegate, hostInstanceName, fieldNameDelegates)
                .beginControlFlow("if ($L == null)", varNameDelegate)
                .addStatement("final $T $L = new $T($L)", presenter, presenterName, presenter, argsCode.toString())
                .addStatement("$L = new $T<>($L, $L.getClass(), id)", varNameDelegate, getClassNameDelegate(), presenterName, argNameView)
                .addStatement("$L.setListener($L)", varNameDelegate, hostInstanceName)
                .addStatement("$L.$L.put(id, $L)", hostInstanceName, fieldNameDelegates, varNameDelegate)
                .addStatement("$L.addLifecycleListener($L)", argNameView, varNameDelegate)
                .endControlFlow()
                .addStatement("(($L) $L).$L = $L.getPresenter()", view.simpleName(), argNameView, fieldName, varNameDelegate)
                .returns(void.class);
    }

    @Override
    protected ClassName getClassNameViewType() {
        return CLASS_NAME_CONDUCTOR;
    }

    @Override
    protected ClassName getClassNameDelegate() {
        return CLASS_NAME_SLICK_CONDUCTOR_DELEGATE;
    }
}
