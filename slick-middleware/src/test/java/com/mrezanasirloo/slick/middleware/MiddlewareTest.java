/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick.middleware;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MiddlewareTest {

    private JavaFileObject readFile(String path) {
        try {
            return JavaFileObjects.forResource(new File("src/test/resources/" + path).toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    public void requestGeneric() {
        JavaFileObject routerSource = readFile("RouterGeneric.java");
        JavaFileObject genSource = readFile("RouterGenericSlick.java");
        JavaFileObject middlewareSource = readFile("MiddlewareNoOp.java");
        JavaFileObject middlewareSource2 = readFile("MiddlewareNoOp2.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(routerSource, middlewareSource, middlewareSource2))
                .processedWith(new MiddlewareProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void requestSimple() {
        JavaFileObject routerSource = readFile("RouterSimple.java");
        JavaFileObject genSource = readFile("RouterSimpleSlick.java");
        JavaFileObject middlewareSource = readFile("MiddlewareNoOp.java");
        JavaFileObject middlewareSource2 = readFile("MiddlewareNoOp2.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(routerSource, middlewareSource, middlewareSource2))
                .processedWith(new MiddlewareProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void requestRx() {
        JavaFileObject routerSource = readFile("RouterRx.java");
        JavaFileObject genSource = readFile("RouterRxSlick.java");
        JavaFileObject middlewareSource = readFile("MiddlewareNoOp.java");
        JavaFileObject middlewareSource2 = readFile("MiddlewareNoOp2.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(routerSource, middlewareSource, middlewareSource2))
                .processedWith(new MiddlewareProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void requestCompletable() {
        JavaFileObject routerSource = readFile("RouterCompletable.java");
        JavaFileObject genSource = readFile("RouterCompletableSlick.java");
        JavaFileObject middlewareSource = readFile("MiddlewareNoOp.java");
        JavaFileObject middlewareSource2 = readFile("MiddlewareNoOp2.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(routerSource, middlewareSource, middlewareSource2))
                .processedWith(new MiddlewareProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void middlewareDagger() {
        JavaFileObject routerSource = readFile("RouterDagger.java");
        JavaFileObject genSource = readFile("RouterDaggerSlick.java");
        JavaFileObject middlewareSource = readFile("MiddlewareNoOp.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(routerSource, middlewareSource))
                .processedWith(new MiddlewareProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

}