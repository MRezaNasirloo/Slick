package com.mrezanasirloo.slick.sample.conductor;

import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class PresenterConductor extends SlickPresenterTestable<ViewConductor> {

    private static final String TAG = PresenterConductor.class.getSimpleName();

    @Override
    public void onViewUp(ViewConductor view) {
        Log.d(TAG, "onViewUp() called hashCode: " + hashCode());
        super.onViewUp(view);
    }

    @Override
    public void onViewDown() {
        Log.d(TAG, "onViewDown() called hashCode: " + hashCode());
        super.onViewDown();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called hashCode: " + hashCode());
        super.onDestroy();
    }
}
