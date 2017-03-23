package com.github.slick.middleware.components;

import com.github.slick.middleware.AnnotatedMethod;
import com.github.slick.middleware.ContainerClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeParameterElement;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-22
 */

public class MiddlewareGeneratorBaseImpl implements MiddlewareGenerator {
    @Override
    public TypeSpec generate(ContainerClass container, List<AnnotatedMethod> annotatedMethods) {

        List<TypeVariableName> typeVariableNames = new ArrayList<>(container.getTypeParameters().size());
        for (TypeParameterElement element : container.getTypeParameters()) {
            final TypeName typeName = TypeVariableName.get(element.asType());
            typeVariableNames.add((TypeVariableName) typeName);
        }


        List<MethodSpec> methodSpecs = new ArrayList<>(annotatedMethods.size());
        for (AnnotatedMethod am : annotatedMethods) {
            final TypeName requestDataType;
            final String requestDataArg;
            final String requestCallback;
            final int size = am.getArgs().size();
            if (size > 0 && size <= 2) {
                requestDataType = ClassName.get(am.getArgs().get(0).asType());
                requestDataArg = am.getArgs().get(0).toString();
                if (size == 2) {
                    requestCallback = am.getArgs().get(1).toString();
                } else {
                    requestCallback = null;
                }
            } else if (size == 0) {
                requestDataType = ClassName.get(Void.class);
                requestDataArg = null;
                requestCallback = null;
            } else {
                //only 2 param allowed
                throw new RuntimeException();
                // TODO: 2017-03-22 error
            }
            final ParameterizedTypeName requestType =
                    ParameterizedTypeName.get(am.getMethodType().requestType,
                            am.getReturnType(), requestDataType);


            final MethodSpec methodSpec = MethodSpec.overriding(am.getSuperMethod())
                    .beginControlFlow("final $T request = new $T()", requestType, requestType)
                    .addCode("@Override ")
                    .beginControlFlow("public $T destination($T data)", am.getReturnType(), requestDataType)
                    .addStatement("return $T.super.$L(data, null)", container.getClassName(), am.getMethodName())
                    .endControlFlow()
                    .endControlFlow("")
                    .addStatement("request.with($L).through($L).destination($L)", requestDataArg,
                            am.getMiddlewareFieldNames(), requestCallback)
                    .build();

            methodSpecs.add(methodSpec);
        }

        return TypeSpec.classBuilder(container.getSubclass())
                .superclass(container.getSuperClass())
                .addTypeVariables(typeVariableNames)
                .addMethods(methodSpecs)
                .build();
    }
}
