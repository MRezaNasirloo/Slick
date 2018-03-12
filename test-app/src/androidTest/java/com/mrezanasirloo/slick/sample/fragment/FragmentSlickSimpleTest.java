package com.mrezanasirloo.slick.sample.fragment;

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
public class FragmentSlickSimpleTest extends ActivityBaseTest {


    @Before
    public void setUp() throws Exception {
        MainActivity activity = testRule.getActivity();
        activity.startActivity(new Intent(activity, ActivityFragmentHost.class));
        ActivityFragmentHost currentActivity = (ActivityFragmentHost) getCurrentActivity();
        assert currentActivity != null;
        view = (ViewTestable) currentActivity.getFragmentManager().findFragmentByTag("fragment");
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