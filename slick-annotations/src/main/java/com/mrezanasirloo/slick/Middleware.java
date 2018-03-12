package com.mrezanasirloo.slick;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-12
 *
 *         Exprimental, Not ready for production use.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Middleware {
    Class[] value();

    // String[] methods() default {"all"};
}
