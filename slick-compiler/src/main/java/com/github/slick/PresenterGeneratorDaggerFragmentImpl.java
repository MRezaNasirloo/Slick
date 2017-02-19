package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_DELEGATE;
import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_FRAGMENT_DELEGATE;
import static com.github.slick.SlickProcessor.ClASS_NAME_ACTIVITY;
import static com.github.slick.SlickProcessor.ClASS_NAME_FRAGMENT;
import static com.github.slick.SlickProcessor.ClASS_NAME_FRAGMENT_SUPPORT;
import static com.github.slick.SlickProcessor.ClASS_NAME_SLICK_VIEW;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
public class PresenterGeneratorDaggerFragmentImpl extends BasePresenterGeneratorDaggerImpl {


    @Override
    protected MethodSpec.Builder bindMethod(ClassName view, ClassName presenter, ClassName presenterHost,
                                            ClassName classNameDelegate,
                                            String fieldName, String argNameView,
                                            String presenterArgName, TypeVariableName viewGenericType,
                                            ParameterizedTypeName typeNameDelegate, StringBuilder argsCode) {
        return MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(viewGenericType.withBounds(ClASS_NAME_SLICK_VIEW))
                .addParameter(viewGenericType, argNameView)
                .addParameter(presenter, deCapitalize(presenter.simpleName()))
                .beginControlFlow("if ($L == null)", hostInstanceName)
                .addStatement("$L = new $T()", hostInstanceName, presenterHost)
                .addStatement("$L.$L = new $T<>($L, $L.getClass())", hostInstanceName, varNameDelegate,
                        classNameDelegate, deCapitalize(presenter.simpleName()), argNameView)
                .addStatement("$L.$L.setListener($L)", hostInstanceName, varNameDelegate, hostInstanceName)
                .endControlFlow()
                .addStatement("return $L.$L", hostInstanceName, varNameDelegate)
                .returns(typeNameDelegate);
    }

    @Override
    protected Iterable<MethodSpec> addMethods(AnnotatedPresenter ap) {
        final ArrayList<MethodSpec> list = new ArrayList<>(3);
        final TypeVariableName viewGenericType =
                TypeVariableName.get("T", getClassNameViewType(ap.getViewType()))
                        .withBounds(ap.getViewInterface());
        String[] methodNames = {"onStart", "onStop", "onDestroy"};
        for (String name : methodNames) {
            final MethodSpec methodSpec = MethodSpec.methodBuilder(name)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .addTypeVariable(viewGenericType)
                    .addParameter(viewGenericType, "view")
                    .addStatement("$L.$L.$L(view)", hostInstanceName, varNameDelegate, name)
                    .returns(void.class).build();
            list.add(methodSpec);
        }
        return list;
    }

    @Override
    protected ClassName getClassNameViewType(SlickProcessor.ViewType viewType) {
        if (SlickProcessor.ViewType.DAGGER_FRAGMENT.equals(viewType)) {
            return ClASS_NAME_FRAGMENT;
        } else {
            return ClASS_NAME_FRAGMENT_SUPPORT;
        }
    }

    @Override
    protected ClassName getClassNameDelegate() {
        return CLASS_NAME_SLICK_FRAGMENT_DELEGATE;
    }
}
