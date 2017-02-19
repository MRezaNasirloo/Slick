package com.github.slick;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_FRAGMENT_DELEGATE;
import static com.github.slick.SlickProcessor.ClASS_NAME_FRAGMENT;
import static com.github.slick.SlickProcessor.ClASS_NAME_FRAGMENT_SUPPORT;
import static com.github.slick.SlickProcessor.ClASS_NAME_HASH_MAP;
import static com.github.slick.SlickProcessor.ClASS_NAME_SLICK_VIEW;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
public class PresenterGeneratorFragmentImpl extends BasePresenterGeneratorImpl {

    @Override
    protected MethodSpec.Builder bindMethod(ClassName view, ClassName presenter, ClassName presenterHost,
                                            ClassName classNameDelegate, String fieldName, String argNameView,
                                            String presenterArgName, TypeVariableName viewGenericType,
                                            ParameterizedTypeName typeNameDelegate, StringBuilder argsCode) {
        return MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(viewGenericType.withBounds(ClASS_NAME_SLICK_VIEW))
                .addParameter(viewGenericType, argNameView)
                .addStatement("final String id = $T.getFragmentId($L)", classNameDelegate, argNameView)
                .addStatement("if ($L == null) $L = new $T()", hostInstanceName, hostInstanceName, presenterHost)
                .addStatement("$T $L = $L.$L.get(id)", typeNameDelegate, varNameDelegate, hostInstanceName,
                        fieldNameDelegates)
                .beginControlFlow("if ($L == null)", varNameDelegate)
                .addStatement("final $T $L = new $T($L)", presenter, presenterName, presenter, argsCode.toString())
                .addStatement("$L = new $T<>($L, $L.getClass(), id)", varNameDelegate, classNameDelegate,
                        presenterName, argNameView)
                .addStatement("$L.setListener($L)", varNameDelegate, hostInstanceName)
                .addStatement("$L.$L.put(id, $L)", hostInstanceName, fieldNameDelegates, varNameDelegate)
                .endControlFlow()
                .addStatement("(($L) $L).$L = $L.getPresenter()", view.simpleName(), argNameView, fieldName,
                        varNameDelegate)
                .addStatement("return $L", varNameDelegate)
                .returns(typeNameDelegate);
    }

    @Override
    protected MethodSpec.Builder addConstructorParameter(List<PresenterArgs> args,
                                                         MethodSpec.Builder methodBuilder) {
        for (PresenterArgs arg : args) {
            final ParameterSpec.Builder paramBuilder =
                    ParameterSpec.builder(TypeName.get(arg.getType()), arg.getName());
            for (AnnotationMirror annotationMirror : arg.getAnnotations()) {
                paramBuilder.addAnnotation(AnnotationSpec.get(annotationMirror));
            }
            methodBuilder.addParameter(paramBuilder.build());
        }
        return methodBuilder;
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
                    .addStatement("$L.$L.get($L.getFragmentId(view)).$L(view)", hostInstanceName,
                            fieldNameDelegates, getClassNameDelegate().simpleName(), name)
                    .returns(void.class).build();
            list.add(methodSpec);
        }
        return list;
    }

    @Override
    protected ClassName getClassNameViewType(SlickProcessor.ViewType viewType) {
        if (SlickProcessor.ViewType.FRAGMENT.equals(viewType)) {
            return ClASS_NAME_FRAGMENT;
        } else {
            return ClASS_NAME_FRAGMENT_SUPPORT;
        }
    }

    @Override
    protected ClassName getClassNameDelegate() {
        return CLASS_NAME_SLICK_FRAGMENT_DELEGATE;
    }

    @Override
    protected FieldSpec getDelegateField(ParameterizedTypeName typeNameDelegate) {
        final ParameterizedTypeName parametrizedMapTypeName =
                ParameterizedTypeName.get(ClASS_NAME_HASH_MAP, ClASS_NAME_STRING, typeNameDelegate);

        return FieldSpec.builder(parametrizedMapTypeName, fieldNameDelegates)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T<>()", ClASS_NAME_HASH_MAP)
                .build();
    }
}
