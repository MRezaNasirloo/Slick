package com.mrezanasirloo.slick.middleware.components;

import com.mrezanasirloo.slick.Middleware;
import com.mrezanasirloo.slick.middleware.AnnotatedMethod;
import com.mrezanasirloo.slick.middleware.ContainerClass;
import com.squareup.javapoet.TypeSpec;

import java.util.List;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-22
 */

public interface MiddlewareGenerator {

    /**
     * @param container the class which holds the methods with {@link Middleware} annotation.
     * @param annotatedMethods methods with {@link Middleware} annotation.
     * @return typeSpec to generate a class.
     */
    TypeSpec generate(ContainerClass container, List<AnnotatedMethod> annotatedMethods);
}
