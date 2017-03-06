package com.github.slick;

import com.squareup.javapoet.TypeSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */

interface PresenterGenerator {
    /**
     * @param ap annotated presenter object
     * @return typeSpec object
     */
    TypeSpec generate(AnnotatedPresenter ap);
}
