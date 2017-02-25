package com.github.slick;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-24
 */

interface MethodSignatureGenerator {
    MethodSpec.Builder generate(String name, AnnotatedPresenter ap, TypeName returns);
}
