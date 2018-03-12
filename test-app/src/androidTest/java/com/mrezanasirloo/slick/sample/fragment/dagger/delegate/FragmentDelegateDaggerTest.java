package com.mrezanasirloo.slick.sample.fragment.dagger.delegate;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.mrezanasirloo.slick.sample.fragment.FragmentBaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-10
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class FragmentDelegateDaggerTest extends FragmentBaseTest {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return FragmentDelegateDagger.newInstance();
    }

    /**
     * Tests Presenter lifecycle
     */
    @Test
    public void testPresenter() {
        super.testPresenter();
    }
}