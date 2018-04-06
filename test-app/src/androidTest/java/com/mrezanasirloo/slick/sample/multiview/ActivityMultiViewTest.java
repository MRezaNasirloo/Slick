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

package com.mrezanasirloo.slick.sample.multiview;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.mrezanasirloo.slick.sample.MainActivity;
import com.mrezanasirloo.slick.sample.activity.ActivityBaseTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 * Created on: 2018-03-10
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivityMultiViewTest extends ActivityBaseTest {

    private ActivityMultiView currentActivity;

    @Before
    public void setUp() {
        MainActivity activity = testRule.getActivity();
        activity.startActivity(new Intent(activity, ActivityMultiView.class));
        currentActivity = (ActivityMultiView) getCurrentActivity();
        assert currentActivity != null;
    }

    /**
     * Test Presenter lifecycle
     */
    @Test
    public void testPresenter1() {
        super.testPresenter(currentActivity.customView1);
    }

    /**
     * Test Presenter lifecycle
     */
    @Test
    public void testPresenter1_2() {
        super.testPresenter(currentActivity.customView1_2);
    }

    /**
     * Test Presenter lifecycle
     */
    @Test
    public void testPresenter2() {
        super.testPresenter(currentActivity.customView2);
    }

}