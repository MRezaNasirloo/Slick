/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick.middleware.components;

import com.mrezanasirloo.slick.middleware.AnnotatedMethod;
import com.mrezanasirloo.slick.middleware.ContainerClass;
import com.mrezanasirloo.slick.middleware.MiddlewareProcessor;
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

import static com.mrezanasirloo.slick.middleware.MiddlewareProcessor.CLASS_NAME_REQUEST_SIMPLE;

/**
 * @author : M.Reza.Nasirloo@gmail.com
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

            final ParameterizedTypeName parametrizedType =
                    am.getMethodType().requestTypeGenerator.generate(am);


            final MethodSpec.Builder methodBuilder = MethodSpec.overriding(am.getSuperMethod())
                    .beginControlFlow("final $T request = new $T()", parametrizedType, parametrizedType)
                    .addCode("@Override ")
                    .beginControlFlow("public $T target($T data)", am.getReturnType().box(), am.getParamType())
                    .addStatement("$L$T.super.$L($L)", handleReturn(am), container.getSubclass(),
                            am.getMethodName(), getParams(am));
            returnNullIfNeeded(methodBuilder, am)
                    .endControlFlow()
                    .endControlFlow("");
            am.getMethodType().rxSourceGenerator.generate(am, methodBuilder)
                    .addStatement("request.with($L).through($L).destination($L)", am.getParamName(),
                            am.getMiddlewareVarNamesAsString(),
                            CLASS_NAME_REQUEST_SIMPLE.equals(am.getMethodType().requestType) ?
                                    am.getCallbackName() :
                                    "source")
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

    private String getParams(AnnotatedMethod am) {
        String paramString = "";
        final int size = am.getArgs().size();
        if (size == 1) {
            paramString += am.isCallback(0) ? "null" : "data";
        } else if (size == 2) {
            paramString += am.isCallback(0) ? "null, " : "data" + ", ";
            paramString += am.isCallback(1) ? "null" : "data";
        }
        return paramString;
    }

    private MethodSpec.Builder returnNullIfNeeded(MethodSpec.Builder builder, AnnotatedMethod am) {
        if (TypeName.get(void.class).equals(am.getReturnType())) {
            return builder.addStatement("return null");
        }
        return builder;

    }

    private MethodSpec.Builder returnNullIfNeededAtTheEnd(MethodSpec.Builder builder, AnnotatedMethod am) {
        if (!TypeName.get(void.class).equals(am.getReturnType())) {
            if (CLASS_NAME_REQUEST_SIMPLE.equals(am.getMethodType().requestType)) {
                builder.addStatement("return null");
            } else {
                builder.addStatement("return source");
            }
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
