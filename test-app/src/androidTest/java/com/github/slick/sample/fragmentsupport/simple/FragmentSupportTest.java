package com.github.slick.sample.fragmentsupport.simple;

import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;

import com.github.slick.sample.fragmentsupport.FragmentSupportBaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-11
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class FragmentSupportTest extends FragmentSupportBaseTest {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return FragmentSupport.newInstance();
    }

    /**
     * Tests Presenter lifecycle
     */
    @Test
    public void testPresenter() {
        super.testPresenter();
    }
}