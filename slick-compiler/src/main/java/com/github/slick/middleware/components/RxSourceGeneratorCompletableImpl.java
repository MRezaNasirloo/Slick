package com.github.slick.middleware.components;

import com.github.slick.middleware.AnnotatedMethod;
import com.github.slick.middleware.MiddlewareProcessor;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-04-04
 */

public class RxSourceGeneratorCompletableImpl implements RxSourceGenerator {
    @Override
    public MethodSpec.Builder generate(AnnotatedMethod am, MethodSpec.Builder builder) {
        if (!MiddlewareProcessor.CLASS_NAME_REQUEST_SIMPLE.equals(am.getMethodType().requestType))
            builder.addStatement("final $T source = $T.create()", am.getMethodType().bridgeType,
                    am.getMethodType().bridgeType);
        return builder;
    }
}
