package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import javax.inject.Inject;
import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_DELEGATE;
import static com.github.slick.SlickProcessor.ClASS_NAME_ACTIVITY;
import static com.github.slick.SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER;
import static com.github.slick.SlickProcessor.ClASS_NAME_SLICK_VIEW;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
public class PresenterGeneratorActivityDaggerImpl extends BasePresenterGenerator implements PresenterGenerator {
    @Override @Inject
    public TypeSpec generate(AnnotatedPresenter ap) {
        final ClassName view = ap.getViewInterface();
        final ClassName viewType = ap.getViewType();
        final ClassName presenter = ap.getPresenter();
        final ClassName presenterHost = ap.getPresenterHost();
        final String hostInstanceName = "hostInstance";
        final String argNameActivity = "activity";
        final String argNameBind = deCapitalize(ap.getView().simpleName());

        final TypeVariableName type = TypeVariableName.get("T", ClASS_NAME_ACTIVITY);
        final ParameterizedTypeName typeName =
                ParameterizedTypeName.get(CLASS_NAME_SLICK_DELEGATE, view, presenter);

        final FieldSpec delegate = FieldSpec.builder(typeName, "delegate")
                .initializer("new $T()", CLASS_NAME_SLICK_DELEGATE)
                .build();

        final FieldSpec hostInstance = FieldSpec.builder(presenterHost, hostInstanceName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();


        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(type.withBounds(ClASS_NAME_SLICK_VIEW))
                .addParameter(type, argNameBind)
                .addParameter(presenter, deCapitalize(presenter.simpleName()))
                .addStatement("if ($L == null) $L = new $T()", hostInstanceName, hostInstanceName, presenterHost)
                .addStatement("$L.setListener($L, $L)", hostInstanceName, argNameBind, deCapitalize(presenter.simpleName()))
                .returns(void.class);

        final MethodSpec bind = methodBuilder.build();

        final MethodSpec setListener = MethodSpec.methodBuilder("setListener")
                .addModifiers(Modifier.PRIVATE)
                .addParameter(ClASS_NAME_ACTIVITY, argNameActivity)
                .addParameter(presenter, deCapitalize(presenter.simpleName()))
                .addStatement("$L.getApplication().registerActivityLifecycleCallbacks(delegate)", argNameActivity)
                .addStatement("delegate.bind($L, $L.getClass())", deCapitalize(presenter.simpleName()), argNameActivity)
                .addStatement("delegate.setListener(this)")
                .returns(void.class)
                .build();

        final MethodSpec onDestroy = MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$L = null", hostInstanceName)
                .build();


        return TypeSpec.classBuilder(presenterHost)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClASS_NAME_ON_DESTROY_LISTENER)
                .addField(delegate)
                .addField(hostInstance)
                .addMethod(bind)
                .addMethod(setListener)
                .addMethod(onDestroy)
                .build();
    }
}
