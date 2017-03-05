package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.ClASS_NAME_HASH_MAP;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
class PresenterGeneratorFragmentImpl extends BasePresenterGeneratorImpl {

    public PresenterGeneratorFragmentImpl(MethodSignatureGenerator generator) {
        super(generator);
    }

    @Override
    protected MethodSpec.Builder bindMethodBody(MethodSpec.Builder builder, AnnotatedPresenter ap, ClassName view,
                                                ClassName presenter,
                                                ClassName presenterHost,
                                                ClassName classNameDelegate, String fieldName, String argNameView,
                                                String presenterArgName, TypeVariableName viewGenericType,
                                                ParameterizedTypeName typeNameDelegate, String argsCode) {
        return builder
                .addStatement("final String id = $T.getFragmentId($L)", classNameDelegate, argNameView)
                .addStatement("if ($L == null) $L = new $T()", hostInstanceName, hostInstanceName, presenterHost)
                .addStatement("$T $L = $L.$L.get(id)", typeNameDelegate, varNameDelegate, hostInstanceName,
                        fieldNameDelegates)
                .beginControlFlow("if ($L == null)", varNameDelegate)
                .addStatement("final $T $L = new $T($L)", presenter, presenterName, presenter, argsCode)
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
    protected Iterable<MethodSpec> addMethods(AnnotatedPresenter ap) {
        final ArrayList<MethodSpec> list = new ArrayList<>(3);
        String[] methodNames = {"onStart", "onStop", "onDestroy"};
        final MethodSignatureGeneratorDaggerImpl generatorDagger = new MethodSignatureGeneratorDaggerImpl();
        for (String name : methodNames) {
            MethodSpec methodSpec = generatorDagger.generate(name, ap, TypeName.get(void.class))
                    .addStatement("$L.$L.get($T.getFragmentId($L)).$L($L)", hostInstanceName,
                            fieldNameDelegates, ap.getDelegateType(), ap.getViewVarName(), name, ap.getViewVarName())
                    .returns(void.class).build();
            list.add(methodSpec);
        }
        return list;
    }

    @Override
    protected FieldSpec getDelegateField(ParameterizedTypeName typeNameDelegate, AnnotatedPresenter ap) {
        final ParameterizedTypeName parametrizedMapTypeName =
                ParameterizedTypeName.get(ClASS_NAME_HASH_MAP, ClASS_NAME_STRING, typeNameDelegate);

        return FieldSpec.builder(parametrizedMapTypeName, fieldNameDelegates)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T<>()", ClASS_NAME_HASH_MAP)
                .build();
    }
}
