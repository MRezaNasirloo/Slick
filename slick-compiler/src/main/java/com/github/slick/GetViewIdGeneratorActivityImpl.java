package com.github.slick;

import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

class GetViewIdGeneratorActivityImpl implements GetViewIdGenerator {
    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return builder.addStatement("final String id = $T.getActivityId($L)", ap.getDelegateType(), ap.getViewVarName());
    }
}
