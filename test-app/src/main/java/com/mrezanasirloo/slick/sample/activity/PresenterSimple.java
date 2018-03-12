package com.mrezanasirloo.slick.sample.activity;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class PresenterSimple extends SlickPresenterTestable<ViewSimple> {

    private static final String TAG = PresenterSimple.class.getSimpleName();

    public PresenterSimple(@IdRes @NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(ViewSimple view) {
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
