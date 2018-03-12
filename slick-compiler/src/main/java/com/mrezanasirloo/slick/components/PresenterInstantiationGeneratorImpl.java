package com.mrezanasirloo.slick.components;

import com.mrezanasirloo.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-06
 */

public class PresenterInstantiationGeneratorImpl implements PresenterInstantiationGenerator {
    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return builder.addStatement("final $T $L = new $T($L)", ap.getPresenter(), "presenter",
                ap.getPresenter(), ap.getArgsAsString());
    }
}
