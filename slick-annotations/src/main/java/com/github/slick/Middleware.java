package com.github.slick;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-12
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Middleware {
    Class[] value();

    String[] methods() default {"all"};
}
