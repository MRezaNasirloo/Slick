package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.squareup.javapoet.ParameterSpec;

import java.util.ArrayList;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-24
 */
public class MethodSignatureGeneratorDaggerImpl extends MethodSignatureGeneratorImpl {

    @Override
    protected Iterable<ParameterSpec> addExtraParameters(AnnotatedPresenter ap) {
        return new ArrayList<>(0);
    }
}
