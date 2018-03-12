package com.mrezanasirloo.slick.sample.cutstomview.dagger;

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
 *         Created on: 2018-03-10
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivityCustomViewDaggerTest extends ActivityBaseTest {

    @Before
    public void setUp() throws Exception {
        MainActivity activity = testRule.getActivity();
        activity.startActivity(new Intent(activity, ActivityCustomViewDagger.class));
        ActivityCustomViewDagger currentActivity = (ActivityCustomViewDagger) getCurrentActivity();
        assert currentActivity != null;
        view = currentActivity.customView;
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