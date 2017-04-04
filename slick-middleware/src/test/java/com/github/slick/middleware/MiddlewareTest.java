package com.github.slick.middleware;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import java.util.Arrays;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MiddlewareTest {
    @Test
    public void requestGeneric() throws Exception {
        JavaFileObject routerSource = JavaFileObjects.forResource("resources/RouterGeneric.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/RouterGenericSlick.java");
        JavaFileObject middlewareSource = JavaFileObjects.forResource("resources/MiddlewareNoOp.java");
        JavaFileObject middlewareSource2 = JavaFileObjects.forResource("resources/MiddlewareNoOp2.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(routerSource, middlewareSource, middlewareSource2))
                .processedWith(new MiddlewareProcessor())
                .compilesWithoutError()
                        .and()
                        .generatesSources(genSource);
    }
}