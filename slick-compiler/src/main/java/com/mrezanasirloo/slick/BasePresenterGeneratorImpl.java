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

package com.mrezanasirloo.slick;

import com.mrezanasirloo.slick.components.AddMethodGenerator;
import com.mrezanasirloo.slick.components.BindMethodBodyGenerator;
import com.mrezanasirloo.slick.components.MethodSignatureGenerator;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;

import static com.mrezanasirloo.slick.AnnotatedPresenter.DELEGATES_FIELD_NAME;
import static com.mrezanasirloo.slick.AnnotatedPresenter.HOST_INSTANCE_VAR_NAME;
import static com.mrezanasirloo.slick.SlickProcessor.ClASS_NAME_HASH_MAP;
import static com.mrezanasirloo.slick.SlickProcessor.ClASS_NAME_INTERNAL_ON_DESTROY_LISTENER;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-02-05
 */
class BasePresenterGeneratorImpl implements PresenterGenerator {

    private final MethodSignatureGenerator methodSignatureGenerator;
    private final BindMethodBodyGenerator methodBodyGenerator;
    private AddMethodGenerator addMethodGenerator;

    public BasePresenterGeneratorImpl(MethodSignatureGenerator methodSignatureGenerator,
                                      BindMethodBodyGenerator methodBodyGenerator) {
        this.methodSignatureGenerator = methodSignatureGenerator;
        this.methodBodyGenerator = methodBodyGenerator;
    }

    public BasePresenterGeneratorImpl(MethodSignatureGenerator methodSignatureGenerator,
                                      BindMethodBodyGenerator methodBodyGenerator,
                                      AddMethodGenerator addMethodGenerator) {
        this.methodSignatureGenerator = methodSignatureGenerator;
        this.methodBodyGenerator = methodBodyGenerator;
        this.addMethodGenerator = addMethodGenerator;
    }

    @Override
    public TypeSpec generate(AnnotatedPresenter ap, boolean multi) {
        final FieldSpec hostInstance = FieldSpec.builder(ap.getPresenterHost(), HOST_INSTANCE_VAR_NAME)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();

        final MethodSpec.Builder bindMethodSignature =
                methodSignatureGenerator.generate("bind", ap, TypeName.get(void.class));

        final MethodSpec.Builder methodBuilder = methodBodyGenerator.generate(bindMethodSignature, ap);

        TypeSpec.Builder builder = TypeSpec.classBuilder(ap.getPresenterHost())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClASS_NAME_INTERNAL_ON_DESTROY_LISTENER)
                .addField(getDelegateField(ap))
                .addField(hostInstance)
                .addMethod(methodBuilder.build());

        // Don't add the onDestroy method, it's already there.
        if (!multi)
            builder.addMethods(addMethods(ap))
                    .addMethod(onDestroyMethod(ap));
        return builder.build();
    }

    /**
     * @param ap annotated presenter object
     * @return method spec
     */
    public MethodSpec onDestroyMethod(AnnotatedPresenter ap) {
        return MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(int.class, "id").build())
                .addStatement("$L.$L.remove(id)", HOST_INSTANCE_VAR_NAME, DELEGATES_FIELD_NAME)
                .beginControlFlow("if ($L.$L.size() == 0)", HOST_INSTANCE_VAR_NAME, DELEGATES_FIELD_NAME)
                .addStatement("$L = null", HOST_INSTANCE_VAR_NAME)
                .endControlFlow()
                .build();
    }

    /**
     * @param ap annotated presenter object
     * @return delegate field
     */
    private FieldSpec getDelegateField(AnnotatedPresenter ap) {
        final ParameterizedTypeName parametrizedMapTypeName =
                ParameterizedTypeName.get(ClASS_NAME_HASH_MAP, ap.getParametrizedDelegateType());

        return FieldSpec.builder(parametrizedMapTypeName, DELEGATES_FIELD_NAME)
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T<>()", ClASS_NAME_HASH_MAP)
                .build();
    }

    /**
     * Utility to add extra arbitrary methods
     *
     * @param ap annotated presenter object
     * @return a list of method specs
     */
    private Iterable<MethodSpec> addMethods(AnnotatedPresenter ap) {
        if (addMethodGenerator != null) {
            return addMethodGenerator.generate(ap);
        }
        return new ArrayList<>(0);
    }

}
