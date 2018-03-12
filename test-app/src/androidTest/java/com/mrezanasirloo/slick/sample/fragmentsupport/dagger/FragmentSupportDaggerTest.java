package com.mrezanasirloo.slick.sample.fragmentsupport.dagger;

import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import com.mrezanasirloo.slick.sample.fragmentsupport.FragmentSupportBaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-11
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class FragmentSupportDaggerTest extends FragmentSupportBaseTest {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return FragmentSupportDagger.newInstance();
    }

    /**
     * Tests Presenter lifecycle
     */
    @Test
    public void testPresenter() {
        super.testPresenter();
    }
}