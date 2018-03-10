package com.github.slick.sample.cutstomview.dagger;

import android.content.Intent;

import com.github.slick.sample.MainActivity;
import com.github.slick.sample.activity.ActivityBaseTest;

import org.junit.Before;
import org.junit.Test;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-10
 */
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