package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

public class GetViewIdGeneratorFragmentImpl implements GetViewIdGenerator {
    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return builder.addStatement("final String id = $T.getFragmentId($L)", ap.getDelegateType(), ap.getViewVarName());
    }
}
