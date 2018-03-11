package com.github.slick.supportfragment;

import com.github.slick.SlickProcessor;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import java.util.Arrays;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */

public class SupportFragmentTest {

    @Test
    public void fragment() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/ExampleFragment.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/ExamplePresenter_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void fragmentDagger() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/dagger/DaggerFragment.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/dagger/ExamplePresenter_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }
}
