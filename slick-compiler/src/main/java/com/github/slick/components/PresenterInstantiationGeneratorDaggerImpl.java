package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

public class PresenterInstantiationGeneratorDaggerImpl implements PresenterInstantiationGenerator {
    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return builder.addStatement("final $T presenter = (($T) $L).$L.get()", ap.getPresenter(), ap.getView(),
                ap.getViewVarName(), ap.getPresenterProvider());
    }
}
