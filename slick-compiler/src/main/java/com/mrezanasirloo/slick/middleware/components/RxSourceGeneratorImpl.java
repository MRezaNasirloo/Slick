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
import com.mrezanasirloo.slick.middleware.MiddlewareProcessor;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-04-04
 */

public class RxSourceGeneratorImpl implements RxSourceGenerator {
    @Override
    public MethodSpec.Builder generate(AnnotatedMethod am, MethodSpec.Builder builder) {
        if (!MiddlewareProcessor.CLASS_NAME_REQUEST_SIMPLE.equals(am.getMethodType().requestType))
            builder.addStatement("final $T<$T> source = $T.create()", am.getMethodType().bridgeType,
                    am.getReturnTypeArg(), am.getMethodType().bridgeType);
        return builder;
    }
}
