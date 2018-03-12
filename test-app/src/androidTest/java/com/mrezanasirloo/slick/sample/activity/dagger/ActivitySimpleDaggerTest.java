package com.mrezanasirloo.slick.sample.activity.dagger;


import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.mrezanasirloo.slick.sample.MainActivity;
import com.mrezanasirloo.slick.sample.activity.ActivityBaseTest;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivitySimpleDaggerTest extends ActivityBaseTest {

    @Before
    public void setUp() throws Exception {
        MainActivity activity = testRule.getActivity();
        activity.startActivity(new Intent(activity, ActivitySimpleDagger.class));
        view = (ViewTestable) getCurrentActivity();
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
