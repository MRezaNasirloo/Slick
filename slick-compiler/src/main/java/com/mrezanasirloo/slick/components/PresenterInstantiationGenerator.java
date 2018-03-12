package com.mrezanasirloo.slick.components;

import com.mrezanasirloo.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-02-24
 */

public interface PresenterInstantiationGenerator {
    MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap);
}
