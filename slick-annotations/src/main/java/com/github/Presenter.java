package com.github;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-01
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Presenter {
    Class<?> value();
}
