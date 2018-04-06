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

package com.mrezanasirloo.slick.sample.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.espresso.core.internal.deps.guava.collect.Iterables;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.mrezanasirloo.slick.SlickPresenter;
import com.mrezanasirloo.slick.sample.MainActivity;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import org.junit.Rule;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.pressBack;
import static junit.framework.Assert.assertEquals;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-10
 */

public class ActivityBaseTest {

    @NonNull
    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    protected ViewTestable view;

    /**
     * Test Presenter lifecycle
     */
    protected void testPresenter() {
        //NOTE: These method should be called in order there are
        getInstrumentation().waitForIdleSync();
        testStart(view.presenter());
        testPreservePresenterOnRotate(view, view.presenter());
        testOnDestroy(view.presenter());
    }

    /**
     * Test Presenter lifecycle
     */
    protected void testPresenter(ViewTestable view) {
        //NOTE: These method should be called in order there are
        getInstrumentation().waitForIdleSync();
        testStart(view.presenter());
        testPreservePresenterOnRotate(view, view.presenter());
        testOnDestroy(view.presenter());
    }

    /**
     * Ensures the {@link SlickPresenter#onViewUp(Object)} called only once during activity start up.
     *
     * @param presenter in question presenter
     */
    protected void testStart(SlickPresenterTestable presenter) {
        assertEquals("onViewUp called on start", 1, presenter.onViewUpCount());
        assertEquals("onViewDown not called on start", 0, presenter.onViewDownCount());
        assertEquals("onDestroy not called on start", 0, presenter.onDestroyCount());
    }

    /**
     * Ensures the Presenter has been preserved during screen rotation
     *
     * @param view      the view it owns this presenter
     * @param presenter in question presenter
     */
    protected void testPreservePresenterOnRotate(ViewTestable view, SlickPresenterTestable presenter) {
        int hashCode = presenter.hashCode();
        rotateScreen();
        getInstrumentation().waitForIdleSync();
        int hashCodeNew = view.presenter().hashCode();

        // TODO: 2018-03-11 Use idling resources
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        assertEquals("Presenters are the same after screen rotation.", hashCode, hashCodeNew);
        assertEquals("onViewDown called once before rotation", 1, presenter.onViewDownCount());
        assertEquals("onViewUp called after rotation", 2, presenter.onViewUpCount());
        assertEquals("onDestroy not called with screen rotation", 0, presenter.onDestroyCount());

    }

    /**
     * Ensures {@link SlickPresenter#onDestroy()} method get called after activity finishes.
     *
     * @param presenter in question presenter
     */
    protected void testOnDestroy(SlickPresenterTestable presenter) {
        pressBack();
        getInstrumentation().waitForIdleSync();
        assertEquals("onViewDown called after back press", 2, presenter.onViewDownCount());
        assertEquals("onDestroy called once after activity finished", 1, presenter.onDestroyCount());
    }

    /**
     * @return The top activity on the back stack
     */
    @Nullable
    protected Activity getCurrentActivity() {
        getInstrumentation().waitForIdleSync();
        final Activity[] activity = new Activity[1];
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                java.util.Collection<Activity> activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                activity[0] = Iterables.getOnlyElement(activities);
            }
        });
        return activity[0];
    }

    /**
     * Rotate screen during test
     */
    protected void rotateScreen() {
        Context context = getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = getCurrentActivity();
        assert activity != null;
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
