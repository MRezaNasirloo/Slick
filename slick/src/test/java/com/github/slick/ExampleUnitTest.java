package com.github.slick;

import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        System.out.println(classloader.getResource(".").getPath());
        InputStream is = classloader.getResourceAsStream("ExampleActivity.java");
        is.toString();
    }
}