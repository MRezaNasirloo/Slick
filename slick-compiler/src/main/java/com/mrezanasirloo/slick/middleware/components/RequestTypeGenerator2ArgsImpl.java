package com.mrezanasirloo.slick.middleware.components;

import com.mrezanasirloo.slick.middleware.AnnotatedMethod;
import com.squareup.javapoet.ParameterizedTypeName;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-04-04
 */

public class RequestTypeGenerator2ArgsImpl implements RequestTypeGenerator {
    @Override
    public ParameterizedTypeName generate(AnnotatedMethod am) {
                    return ParameterizedTypeName.get(am.getMethodType().requestType,
                            am.getReturnType().box(), am.getParamType());
    }
}
