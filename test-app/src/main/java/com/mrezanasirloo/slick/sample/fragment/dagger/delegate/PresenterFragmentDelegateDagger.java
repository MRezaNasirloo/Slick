package com.mrezanasirloo.slick.sample.fragment.dagger.delegate;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import javax.inject.Inject;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public class PresenterFragmentDelegateDagger extends SlickPresenterTestable<ViewFragmentDelegateDagger> {

    private static final String TAG = PresenterFragmentDelegateDagger.class.getSimpleName();

    @Inject
    public PresenterFragmentDelegateDagger(@NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(ViewFragmentDelegateDagger view) {
        Log.d(TAG, "onViewUp() called");
        super.onViewUp(view);
    }

    @Override
    public void onViewDown() {
        Log.d(TAG, "onViewDown() called");
        super.onViewDown();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
    }
}
