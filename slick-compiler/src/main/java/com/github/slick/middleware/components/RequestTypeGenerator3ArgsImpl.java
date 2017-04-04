package com.github.slick.middleware.components;

import com.github.slick.middleware.AnnotatedMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-04-04
 */

public class RequestTypeGenerator3ArgsImpl implements RequestTypeGenerator {
    @Override
    public ParameterizedTypeName generate(AnnotatedMethod am) {
                    return ParameterizedTypeName.get(am.getMethodType().requestType,
                            am.getReturnType().box(), am.getReturnType() instanceof ParameterizedTypeName ? ((ParameterizedTypeName) am.getReturnType()).typeArguments.get(0) :
                                    ClassName.get(Object.class), am.getParamType());
    }
}
