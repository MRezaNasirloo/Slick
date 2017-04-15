package com.github.slick.middleware.components;

import com.github.slick.middleware.AnnotatedMethod;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-04-04
 */

public interface RxSourceGenerator {
    MethodSpec.Builder generate(AnnotatedMethod am, MethodSpec.Builder builder);
}
