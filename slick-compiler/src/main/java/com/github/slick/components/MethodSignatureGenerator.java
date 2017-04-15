package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-24
 */

public interface MethodSignatureGenerator {
    MethodSpec.Builder generate(String name, AnnotatedPresenter ap, TypeName returns);
}
