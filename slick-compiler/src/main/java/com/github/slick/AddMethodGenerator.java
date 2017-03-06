package com.github.slick;

import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-24
 */

interface AddMethodGenerator {
    Iterable<MethodSpec> generate(AnnotatedPresenter ap);
}
