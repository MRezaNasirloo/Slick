package com.mrezanasirloo.slick.middleware.components;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;

import java.util.List;
import java.util.Set;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-29
 */

public interface FieldGenerator {
    List<FieldSpec> generate(Set<ClassName> middleware);
}
