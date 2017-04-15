package com.github.slick.middleware.components;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;

import java.util.List;
import java.util.Set;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

public interface FieldGenerator {
    List<FieldSpec> generate(Set<ClassName> middleware);
}
