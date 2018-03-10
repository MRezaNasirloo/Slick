package com.github.slick.sample.fragment.dagger.delegate;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.github.slick.sample.fragment.FragmentBaseTest;

import org.junit.Test;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-10
 */
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