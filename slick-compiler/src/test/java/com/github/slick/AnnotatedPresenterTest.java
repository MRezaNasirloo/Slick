package com.github.slick;

import com.google.testing.compile.JavaFileObjects;
import com.squareup.javapoet.ClassName;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static org.junit.Assert.*;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */
public class AnnotatedPresenterTest {

    @Test
    public void test_logic() {
        final AnnotatedPresenter annotatedPresenter = new AnnotatedPresenter("foo.bar.Classic", null, null,
                null, null,
                null, null);
        assertEquals(ClassName.get("foo.bar", "Classic"), annotatedPresenter.getViewInterface());
        ClassLoader.getSystemResource("Hello.java").toString();
        JavaFileObjects.forResource("Hello.java").toString();
    }

}