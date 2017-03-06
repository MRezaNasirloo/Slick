package com.github.slick;

import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

class PresenterInstantiationGeneratorImpl implements PresenterInstantiationGenerator {
    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return builder.addStatement("final $T $L = new $T($L)", ap.getPresenter(), "presenter",
                ap.getPresenter(), ap.getArgsAsString());
    }
}
