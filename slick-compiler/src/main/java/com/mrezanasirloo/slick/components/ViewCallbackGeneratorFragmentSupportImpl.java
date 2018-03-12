package com.mrezanasirloo.slick.components;

import com.mrezanasirloo.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-06
 */

public class ViewCallbackGeneratorFragmentSupportImpl implements ViewCallbackGenerator {
    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return builder.endControlFlow()
                .addStatement("$L.getFragmentManager().registerFragmentLifecycleCallbacks($L, false)",
                        ap.getViewVarName(), "delegate");
    }
}
