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

package com.mrezanasirloo.slick.supportfragment;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;
import com.mrezanasirloo.slick.SlickProcessor;

import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-02-01
 */

public class SupportFragmentTest {

    private JavaFileObject readFile(String path) {
        try {
            return JavaFileObjects.forResource(new File("src/test/resources/" + path).toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Test
    public void fragment() {
        JavaFileObject sourceViewInterface = readFile("ExampleView.java");
        JavaFileObject sourcePresenter = readFile("ExamplePresenter.java");
        JavaFileObject sourceView = readFile("ExampleFragment.java");
        JavaFileObject genSource = readFile("ExamplePresenter_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void fragmentDagger() {
        JavaFileObject sourceViewInterface = readFile("ExampleView.java");
        JavaFileObject sourcePresenter = readFile("ExamplePresenter.java");
        JavaFileObject sourceView = readFile("dagger/DaggerFragment.java");
        JavaFileObject genSource = readFile("dagger/ExamplePresenter_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }
}
