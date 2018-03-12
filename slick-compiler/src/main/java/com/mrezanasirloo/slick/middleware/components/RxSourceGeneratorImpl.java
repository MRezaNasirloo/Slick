package com.mrezanasirloo.slick.middleware.components;

import com.mrezanasirloo.slick.middleware.AnnotatedMethod;
import com.mrezanasirloo.slick.middleware.MiddlewareProcessor;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
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
