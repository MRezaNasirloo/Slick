package com.mrezanasirloo.slick.sample.fragmentsupport.dagger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import javax.inject.Inject;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class PresenterFragmentSupportDagger extends SlickPresenterTestable<ViewFragmentSupportDagger> {

    private static final String TAG = PresenterFragmentSupportDagger.class.getSimpleName();

    @Inject
    public PresenterFragmentSupportDagger(@NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(ViewFragmentSupportDagger view) {
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
