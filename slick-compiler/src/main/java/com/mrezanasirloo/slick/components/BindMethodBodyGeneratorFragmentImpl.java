package com.mrezanasirloo.slick.components;

import com.mrezanasirloo.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

public class BindMethodBodyGeneratorFragmentImpl extends BindMethodBodyGeneratorImpl {
    public BindMethodBodyGeneratorFragmentImpl(GetViewIdGenerator getViewIdGenerator,
                                               PresenterInstantiationGenerator presenterInstantiationGenerator,
                                               ViewCallbackGenerator viewCallbackGenerator) {
        super(getViewIdGenerator, presenterInstantiationGenerator, viewCallbackGenerator);
    }

    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {
        return super.generate(builder, ap)
                .returns(ap.getParametrizedDelegateType())
                .addStatement("return delegate");
    }
}
