package com.mrezanasirloo.slick.sample.fragmentsupport.simple;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class PresenterFragmentSupport extends SlickPresenterTestable<ViewFragmentSupport> {

    private static final String TAG = PresenterFragmentSupport.class.getSimpleName();

    public PresenterFragmentSupport(@NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(ViewFragmentSupport view) {
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
