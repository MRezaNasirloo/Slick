package com.mrezanasirloo.slick.sample.fragment;

import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class PresenterFragmentSimple extends SlickPresenterTestable<ViewFragmentSimple> {

    private static final String TAG = PresenterFragmentSimple.class.getSimpleName();

    public PresenterFragmentSimple(int integer, String s) {
    }

    @Override
    public void onViewUp(ViewFragmentSimple view) {
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
