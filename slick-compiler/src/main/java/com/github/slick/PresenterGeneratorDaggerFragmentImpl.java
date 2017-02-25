package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
class PresenterGeneratorDaggerFragmentImpl extends BasePresenterGeneratorDaggerImpl {


    public PresenterGeneratorDaggerFragmentImpl(MethodSignatureGenerator generator) {
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
                .addStatement("$L.$L.setListener($L)", hostInstanceName, varNameDelegate, hostInstanceName)
                .endControlFlow()
                .addStatement("return $L.$L", hostInstanceName, varNameDelegate)
                .returns(typeNameDelegate);
    }

    @Override
    protected Iterable<MethodSpec> addMethods(AnnotatedPresenter ap) {
        final ArrayList<MethodSpec> list = new ArrayList<>(3);
        String[] methodNames = {"onStart", "onStop", "onDestroy"};
        for (String name : methodNames) {
            final MethodSpec.Builder builder = generator.generate(name, ap, TypeName.get(void.class));
            final MethodSpec methodSpec = builder
                    .addStatement("$L.$L.$L($L)", hostInstanceName, varNameDelegate, name, ap.getViewVarName())
                    .returns(void.class).build();
            list.add(methodSpec);
        }
        return list;
    }

}
