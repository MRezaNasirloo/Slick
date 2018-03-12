package com.mrezanasirloo.slick.components;

import com.mrezanasirloo.slick.AnnotatedPresenter;
import com.squareup.javapoet.ParameterSpec;

import java.util.ArrayList;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-02-24
 */
public class MethodSignatureGeneratorDaggerImpl extends MethodSignatureGeneratorImpl {

    @Override
    protected Iterable<ParameterSpec> addExtraParameters(AnnotatedPresenter ap) {
        return new ArrayList<>(0);
    }
}
