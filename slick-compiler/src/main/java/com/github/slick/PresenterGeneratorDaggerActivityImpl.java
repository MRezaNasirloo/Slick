package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
class PresenterGeneratorDaggerActivityImpl extends PresenterGeneratorActivityImpl {


    public PresenterGeneratorDaggerActivityImpl(MethodSignatureGenerator generator) {
        super(generator);
    }

    @Override
    protected MethodSpec.Builder bindMethodBody(MethodSpec.Builder builder, AnnotatedPresenter ap, ClassName view,
                                                ClassName presenter, ClassName presenterHost,
                                                ClassName classNameDelegate, String fieldName, String argNameView,
                                                String presenterArgName, TypeVariableName viewGenericType,
                                                ParameterizedTypeName typeNameDelegate, String argsCode) {
        if (ap.multiInstance()) return super.bindMethodBody(builder, ap, view, presenter, presenterHost, classNameDelegate, fieldName,
                argNameView, presenterArgName, viewGenericType, typeNameDelegate, argsCode);
        return builder
                .beginControlFlow("if ($L == null)", hostInstanceName)
                .addStatement("$L = new $T()", hostInstanceName, presenterHost)
                .addStatement("$T presenter = (($T) $L).$L", presenter, view, argNameView, fieldName)
                .addStatement("$L.$L = new $T<>($L, $L.getClass())", hostInstanceName, varNameDelegate,
                        classNameDelegate, presenterName, argNameView)
                .addStatement("$L.getApplication().registerActivityLifecycleCallbacks($L.$L)", argNameView,
                        hostInstanceName, varNameDelegate)
                .addStatement("$L.$L.setListener($L)", hostInstanceName, varNameDelegate, hostInstanceName)
                .endControlFlow();
    }

    @Override
    protected MethodSpec.Builder presenterInstantiating(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return builder.addStatement("final $T presenter = (($T) $L).$L.get()", ap.getPresenter(), ap.getView(),
                SlickProcessor.deCapitalize(ap.getView().simpleName()), ap.getPresenterProvider());
    }

    @Override
    public MethodSpec onDestroyMethod(AnnotatedPresenter ap) {
        if (ap.multiInstance()) return super.onDestroyMethod(ap);
        return MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClASS_NAME_STRING, "id").build())
                .addStatement("$L = null", hostInstanceName)
                .build();
    }

    @Override
    protected FieldSpec getDelegateField(ParameterizedTypeName typeNameDelegate, AnnotatedPresenter ap) {
        if (ap.multiInstance()) return super.getDelegateField(typeNameDelegate, ap);
        return FieldSpec.builder(typeNameDelegate, varNameDelegate)
                .build();
    }
}
