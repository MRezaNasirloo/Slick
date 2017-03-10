package com.github.slick;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */

public class PresenterGeneratorsTest {

    @Test
    public void activity() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/ExampleActivity.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/ExampleActivity_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void activityDagger() {

        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/dagger/DaggerActivity.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/dagger/DaggerActivity_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }


    @Test
    public void fragment() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/ExampleFragment.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/ExampleFragment_Slick.java");

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
        JavaFileObject genSource = JavaFileObjects.forResource("resources/dagger/DaggerFragment_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void CustomView() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/ExampleCustomView.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/ExampleCustomView_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }
}
