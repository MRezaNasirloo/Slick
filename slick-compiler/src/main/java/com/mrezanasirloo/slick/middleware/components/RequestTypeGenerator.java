package com.mrezanasirloo.slick.middleware.components;

import com.mrezanasirloo.slick.middleware.AnnotatedMethod;
import com.squareup.javapoet.ParameterizedTypeName;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-04-04
 */

public interface RequestTypeGenerator {
    ParameterizedTypeName generate(AnnotatedMethod am);
}
