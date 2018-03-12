package com.mrezanasirloo.slick.components;

import com.mrezanasirloo.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-24
 */

public interface ViewCallbackGenerator {
    MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap);
}
