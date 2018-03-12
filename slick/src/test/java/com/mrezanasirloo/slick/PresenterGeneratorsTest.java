package com.mrezanasirloo.slick;

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

public class PresenterGeneratorsTest {

    @Test
    public void activity() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/activity/ExampleActivity.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/activity/ExamplePresenter_Slick.java");

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
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/dagger/activity/DaggerActivity.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/dagger/activity/ExamplePresenter_Slick.java");

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
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/fragment/ExampleFragment.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/fragment/ExamplePresenter_Slick.java");

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
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/dagger/fragment/DaggerFragment.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/dagger/fragment/ExamplePresenter_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void customView() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/view/ExampleCustomView.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/view/ExamplePresenter_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void daggerCustomView() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/dagger/view/DaggerCustomView.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/dagger/view/ExamplePresenter_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }
}
