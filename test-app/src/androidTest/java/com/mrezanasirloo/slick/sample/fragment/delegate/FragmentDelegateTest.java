package com.mrezanasirloo.slick.sample.fragment.delegate;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.mrezanasirloo.slick.sample.fragment.FragmentBaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-10
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class FragmentDelegateTest extends FragmentBaseTest {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return FragmentDelegate.newInstance();
    }

    /**
     * Test Presenter lifecycle
     */
    @Test
    public void testPresenter() {
        super.testPresenter();
    }

}