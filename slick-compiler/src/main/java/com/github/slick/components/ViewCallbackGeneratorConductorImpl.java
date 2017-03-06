package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;

import static com.github.slick.AnnotatedPresenter.DELEGATE_VAR_NAME;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

public class ViewCallbackGeneratorConductorImpl implements ViewCallbackGenerator {
    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return builder
                .addStatement("$L.addLifecycleListener($L)", ap.getViewVarName(), DELEGATE_VAR_NAME)
                .endControlFlow();
    }
}
