package com.mrezanasirloo.slick.middleware.components;

import com.mrezanasirloo.slick.middleware.ContainerClass;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;

import java.util.Set;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-29
 */

public interface ConstructorGenerator {
    MethodSpec generate(ContainerClass container, Set<ClassName> middleware);
}
