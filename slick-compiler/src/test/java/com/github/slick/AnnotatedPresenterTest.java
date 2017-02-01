package com.github.slick;

import com.squareup.javapoet.ClassName;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */
public class AnnotatedPresenterTest {

    @Test
    public void test_logic() {
        final AnnotatedPresenter annotatedPresenter = new AnnotatedPresenter("foo.bar.Classic");
        assertEquals(ClassName.get("foo.bar", "Classic"), annotatedPresenter.getClassName());
    }

}