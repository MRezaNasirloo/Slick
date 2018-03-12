package com.mrezanasirloo.slick.sample.conductor;

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
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-10
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivityConductorHostTest extends ActivityBaseTest {

    @Before
    public void setUp() throws Exception {
        MainActivity activity = testRule.getActivity();
        activity.startActivity(new Intent(activity, ActivityConductorHost.class));
        ActivityConductorHost currentActivity = (ActivityConductorHost) getCurrentActivity();
        assert currentActivity != null;
        view = (ViewTestable) currentActivity.router.getControllerWithTag("ControllerSimple");
        assert view != null;
    }

    /**
     * Test Presenter lifecycle
     */
    @Test
    public void testPresenter() {
        super.testPresenter();
    }
}