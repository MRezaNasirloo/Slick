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

package com.mrezanasirloo.slick.components;

import com.mrezanasirloo.slick.AnnotatedPresenter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import static com.mrezanasirloo.slick.AnnotatedPresenter.DELEGATES_FIELD_NAME;
import static com.mrezanasirloo.slick.AnnotatedPresenter.DELEGATE_VAR_NAME;
import static com.mrezanasirloo.slick.AnnotatedPresenter.HOST_INSTANCE_VAR_NAME;
import static com.mrezanasirloo.slick.AnnotatedPresenter.PRESENTER_VAR_NAME;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-06
 */

public class BindMethodBodyGeneratorImpl implements BindMethodBodyGenerator {

    private final GetViewIdGenerator getViewIdGenerator;
    private final PresenterInstantiationGenerator presenterInstantiationGenerator;
    private final ViewCallbackGenerator viewCallbackGenerator;

    public BindMethodBodyGeneratorImpl(GetViewIdGenerator getViewIdGenerator,
                                       PresenterInstantiationGenerator presenterInstantiationGenerator,
                                       ViewCallbackGenerator viewCallbackGenerator) {
        this.getViewIdGenerator = getViewIdGenerator;
        this.presenterInstantiationGenerator = presenterInstantiationGenerator;
        this.viewCallbackGenerator = viewCallbackGenerator;
    }

    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {

        final ClassName view = ap.getView();
        final ClassName viewInterface = ap.getViewInterface();
        final ClassName presenterType = ap.getPresenter();
        final ClassName presenterHost = ap.getPresenterHost();
        final String fieldName = ap.getPresenterFieldName();
        final ClassName delegateType = ap.getViewType().delegateType();
        final ParameterizedTypeName typeNameDelegate =
                ParameterizedTypeName.get(delegateType, viewInterface, presenterType);

        getViewIdGenerator
                .generate(builder, ap)
                .addStatement("if ($L == null) $L = new $T()",
                        HOST_INSTANCE_VAR_NAME,
                        HOST_INSTANCE_VAR_NAME,
                        presenterHost)
                .addStatement("$T $L = $L.$L.get(id)",
                        typeNameDelegate,
                        DELEGATE_VAR_NAME,
                        HOST_INSTANCE_VAR_NAME,
                        DELEGATES_FIELD_NAME)
                .beginControlFlow("if ($L == null)", DELEGATE_VAR_NAME);

        presenterInstantiationGenerator.generate(builder, ap)
                .addStatement("$L = new $T<>($L, $L.getClass(), id)",
                        DELEGATE_VAR_NAME,
                        ap.getDelegateType(),
                        PRESENTER_VAR_NAME,
                        ap.getViewVarName())
                .addStatement("$L.setListener($L)",
                        DELEGATE_VAR_NAME,
                        HOST_INSTANCE_VAR_NAME)
                .addStatement("$L.$L.put(id, $L)",
                        HOST_INSTANCE_VAR_NAME,
                        DELEGATES_FIELD_NAME,
                        DELEGATE_VAR_NAME);

        viewCallbackGenerator.generate(builder, ap)
                .addStatement("(($L) $L).$L = $L.getPresenter()",
                        view.simpleName(),
                        ap.getViewVarName(),
                        fieldName,
                        DELEGATE_VAR_NAME);
        return builder;
    }
}
