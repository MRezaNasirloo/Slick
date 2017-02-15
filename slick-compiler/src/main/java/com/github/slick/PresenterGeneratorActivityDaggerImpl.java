package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_DELEGATE;
import static com.github.slick.SlickProcessor.ClASS_NAME_ACTIVITY;
import static com.github.slick.SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER;
import static com.github.slick.SlickProcessor.ClASS_NAME_SLICK_VIEW;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
public class PresenterGeneratorActivityDaggerImpl extends BasePresenterGenerator implements PresenterGenerator {


    @Override
    public TypeSpec generate(AnnotatedPresenter ap) {
        final ClassName view = ap.getViewInterface();
        final ClassName viewType = ap.getViewType();
        final ClassName presenter = ap.getPresenter();
        final ClassName presenterHost = ap.getPresenterHost();
        final String hostInstanceName = "hostInstance";
        final String varNameDelegate = "delegate";
        final String argNameActivity = "activity";
        final String presenterName = deCapitalize(presenter.simpleName());
        final String argNameBind = deCapitalize(ap.getView().simpleName());

        final TypeVariableName type = TypeVariableName.get("T", ClASS_NAME_ACTIVITY);
        final ParameterizedTypeName typeName =
                ParameterizedTypeName.get(CLASS_NAME_SLICK_DELEGATE, view, presenter);

        final FieldSpec delegate = FieldSpec.builder(typeName, "delegate")
                .initializer("new $T<>()", CLASS_NAME_SLICK_DELEGATE)
                .build();

        final FieldSpec hostInstance = FieldSpec.builder(presenterHost, hostInstanceName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();


        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(type.withBounds(ClASS_NAME_SLICK_VIEW))
                .addParameter(type, argNameBind)
                .addParameter(presenter, presenterName)
                .addStatement("if ($L == null) $L = new $T()", hostInstanceName, hostInstanceName, presenterHost)
                .addStatement("$L.getApplication().registerActivityLifecycleCallbacks($L.$L)", argNameBind, hostInstanceName, varNameDelegate)
                .addStatement("$L.$L.bind($L, $L.getClass())", hostInstanceName, varNameDelegate, presenterName, argNameBind)
                .addStatement("$L.$L.setListener($L)", hostInstanceName, varNameDelegate, hostInstanceName)
                .returns(void.class);

        final MethodSpec bind = methodBuilder.build();

        final MethodSpec onDestroy = MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClASS_NAME_STRING, "id").build())
                .addStatement("$L = null", hostInstanceName)
                .build();


        return TypeSpec.classBuilder(presenterHost)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClASS_NAME_ON_DESTROY_LISTENER)
                .addField(delegate)
                .addField(hostInstance)
                .addMethod(bind)
                .addMethod(onDestroy)
                .build();
    }
}
