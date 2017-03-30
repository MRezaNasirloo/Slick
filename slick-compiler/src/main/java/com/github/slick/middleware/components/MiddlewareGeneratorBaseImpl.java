package com.github.slick.middleware.components;

import com.github.slick.middleware.AnnotatedMethod;
import com.github.slick.middleware.ContainerClass;
import com.github.slick.middleware.MiddlewareProcessor;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeParameterElement;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-22
 */

public class MiddlewareGeneratorBaseImpl implements MiddlewareGenerator {

    private final ConstructorGenerator constructorGenerator;
    private final FieldGenerator fieldGenerator;

    public MiddlewareGeneratorBaseImpl(ConstructorGenerator constructorGenerator, FieldGenerator fieldGenerator) {
        this.constructorGenerator = constructorGenerator;
        this.fieldGenerator = fieldGenerator;
    }

    @Override
    public TypeSpec generate(ContainerClass container, List<AnnotatedMethod> annotatedMethods) {
        List<MethodSpec> methodSpecList = new ArrayList<>(annotatedMethods.size());

        Set<ClassName> middleware = new LinkedHashSet<>();

        for (AnnotatedMethod annotatedMethod : annotatedMethods) {
            Collections.addAll(middleware, annotatedMethod.getMiddlewareClassNames());
        }
        final List<FieldSpec> fieldSpecs = fieldGenerator.generate(middleware);
        final MethodSpec constructor = constructorGenerator.generate(container, middleware);
        methodSpecList.add(constructor);


        List<TypeVariableName> typeVariableNames = new ArrayList<>(container.getTypeParameters().size());
        for (TypeParameterElement element : container.getTypeParameters()) {
            final TypeName typeName = TypeVariableName.get(element.asType());
            typeVariableNames.add((TypeVariableName) typeName);
        }


        //add methods
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
                requestCallback = "";
            } else {
                //only 2 param allowed
                throw new RuntimeException();
                // TODO: 2017-03-22 log error with stacktrace
            }
            final ParameterizedTypeName requestType =
                    ParameterizedTypeName.get(am.getMethodType().requestType,
                            am.getReturnType().box(), requestDataType);


            final MethodSpec.Builder methodBuilder = MethodSpec.overriding(am.getSuperMethod())
                    .beginControlFlow("final $T request = new $T()", requestType, requestType)
                    .addCode("@Override ")
                    .beginControlFlow("public $T target($T data)", am.getReturnType().box(), requestDataType)
                    .addStatement("$L$T.super.$L(data$L)", handleReturn(am), container.getSubclass(),
                            am.getMethodName(), requestCallback != null ? ", null" : "");
            returnNullIfNeeded(methodBuilder, am)
                    .endControlFlow()
                    .endControlFlow("")
                    .addStatement("request.with($L).through($L).destination($L)", requestDataArg,
                            am.getMiddlewareVarNamesAsString(), requestCallback)
                    .addStatement("$T.getInstance().push(request).processLastRequest()",
                            MiddlewareProcessor.CLASS_NAME_REQUEST_STACK);
            returnNullIfNeededAtTheEnd(methodBuilder, am);

            methodSpecList.add(methodBuilder.build());
        }

        return TypeSpec.classBuilder(container.getSubclass())
                .addModifiers(Modifier.PUBLIC)
                .superclass(container.getSuperClass())
                .addTypeVariables(typeVariableNames)
                .addMethods(methodSpecList)
                .addFields(fieldSpecs)
                .build();
    }

    private MethodSpec.Builder returnNullIfNeeded(MethodSpec.Builder builder, AnnotatedMethod am) {
        if (TypeName.get(void.class).equals(am.getReturnType())) {
            return builder.addStatement("return null");
        }
        return builder;

    }

    private MethodSpec.Builder returnNullIfNeededAtTheEnd(MethodSpec.Builder builder, AnnotatedMethod am) {
        if (!TypeName.get(void.class).equals(am.getReturnType())) {
            return builder.addStatement("return null");
        }
        return builder;

    }

    private String handleReturn(AnnotatedMethod am) {
        if (TypeName.get(void.class).equals(am.getReturnType())) {
            return "";
        }
        return "return ";
    }
}
