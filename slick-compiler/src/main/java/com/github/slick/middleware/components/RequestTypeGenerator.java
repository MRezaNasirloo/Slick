package com.github.slick.middleware.components;

import com.github.slick.middleware.AnnotatedMethod;
import com.squareup.javapoet.ParameterizedTypeName;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-04-04
 */

public interface RequestTypeGenerator {
    ParameterizedTypeName generate(AnnotatedMethod am);
}
