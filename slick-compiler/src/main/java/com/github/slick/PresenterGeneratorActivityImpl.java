package com.github.slick;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_DELEGATE;
import static com.github.slick.SlickProcessor.ClASS_NAME_HASH_MAP;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
class PresenterGeneratorActivityImpl extends BasePresenterGeneratorImpl {
    public PresenterGeneratorActivityImpl(MethodSignatureGenerator generator) {
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
                .addStatement("final String id = $T.getActivityId($L)", classNameDelegate, argNameView)
                .addStatement("if ($L == null) $L = new $T()", hostInstanceName, hostInstanceName, presenterHost)
                .addStatement("$T $L = $L.$L.get(id)", typeNameDelegate, varNameDelegate, hostInstanceName,
                        fieldNameDelegates)
                .beginControlFlow("if ($L == null)", varNameDelegate)
                .addStatement("final $T $L = new $T($L)", presenter, presenterName, presenter, argsCode)
                .addStatement("$L = new $T<>($L, $L.getClass(), id)", varNameDelegate, CLASS_NAME_SLICK_DELEGATE,
                        presenterName, argNameView)
                .addStatement("$L.setListener($L)", varNameDelegate, hostInstanceName)
                .addStatement("$L.$L.put(id, $L)", hostInstanceName, fieldNameDelegates, varNameDelegate)
                .addStatement("$L.getApplication().registerActivityLifecycleCallbacks(delegate)", argNameView)
                .endControlFlow()
                .addStatement("(($L) $L).$L = $L.getPresenter()", view.simpleName(), argNameView, fieldName,
                        varNameDelegate)
                .returns(void.class);
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
