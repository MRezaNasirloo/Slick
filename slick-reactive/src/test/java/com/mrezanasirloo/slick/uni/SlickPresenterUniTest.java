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

package com.mrezanasirloo.slick.uni;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.PublishSubject;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-11
 */
public class SlickPresenterUniTest {

    private ViewExample view;
    private TestScheduler testScheduler;
    private SlickPresenterUniExample presenter;
    private PublishSubject<Boolean> likePost;
    private PublishSubject<Integer> loadText;

    @Before
    public void setUp() throws Exception {
        likePost = PublishSubject.create();
        loadText = PublishSubject.create();

        view = Mockito.mock(ViewExample.class);
        Mockito.when(view.likeComment()).thenReturn(likePost);
        Mockito.when(view.loadText()).thenReturn(loadText);

        testScheduler = new TestScheduler();
        presenter = new SlickPresenterUniExample(testScheduler, testScheduler);
    }


    @Test
    public void testPresenterBehavior() throws Exception {
        //Views has attached to screen
        presenter.onViewUp(view);
        Assert.assertTrue(presenter.hasSubscribed());

        Mockito.verify(view, Mockito.times(1)).setLike(false);
        Mockito.verify(view, Mockito.never()).setText(null);

        likePost.onNext(true);
        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);

        Mockito.verify(view, Mockito.times(1)).setLike(true);
        Mockito.verify(view, Mockito.never()).setText(null);

        loadText.onNext(1);

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);

        Mockito.verify(view, Mockito.times(2)).setLike(true);
        Mockito.verify(view, Mockito.times(1)).setText("Foo Bar");

        //Ensures the state is delivered after view is attached again, simulates screen rotation
        presenter.onViewDown();
        presenter.onViewUp(view);

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS);

        Mockito.verify(view, Mockito.times(3)).setLike(true);
        Mockito.verify(view, Mockito.times(2)).setText("Foo Bar");

    }
}