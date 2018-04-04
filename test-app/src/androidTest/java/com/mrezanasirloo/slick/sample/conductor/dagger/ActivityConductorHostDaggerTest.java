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

package com.mrezanasirloo.slick.sample.conductor.dagger;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.mrezanasirloo.slick.sample.MainActivity;
import com.mrezanasirloo.slick.sample.activity.ActivityBaseTest;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-10
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivityConductorHostDaggerTest extends ActivityBaseTest {

    @Before
    public void setUp() {
        MainActivity activity = testRule.getActivity();
        activity.startActivity(new Intent(activity, ActivityConductorHostDagger.class));
        ActivityConductorHostDagger currentActivity = (ActivityConductorHostDagger) getCurrentActivity();
        assert currentActivity != null;
        view = (ViewTestable) currentActivity.router.getControllerWithTag("ControllerDagger");
        assert view != null;
    }

    /**
     * Tests Presenter lifecycle
     */
    @Test
    public void testPresenter() {
        super.testPresenter();
    }
}