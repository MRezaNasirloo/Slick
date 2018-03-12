package com.mrezanasirloo.slick.conductor;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;
import com.mrezanasirloo.slick.SlickProcessor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-16
 */

public class ConductorTest {
    @Test
    public void controller() throws Exception {
        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/PresenterSimple.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/ExampleController.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/PresenterSimple_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);

        final List<JavaFileObject> target = new ArrayList<>(3);
        target.add(sourcePresenter);
        target.add(sourceView);
        target.add(sourceViewInterface);
        assertAbout(JavaSourcesSubjectFactory.javaSources()).that(target)
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void conductorDagger() {

        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/PresenterDagger.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/DaggerController.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/PresenterDagger_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }
}
