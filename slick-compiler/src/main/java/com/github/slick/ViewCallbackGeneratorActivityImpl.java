package com.github.slick;

import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

class ViewCallbackGeneratorActivityImpl implements ViewCallbackGenerator {
    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return builder.addStatement("$L.getApplication().registerActivityLifecycleCallbacks(delegate)",
                ap.getViewVarName());
    }
}
