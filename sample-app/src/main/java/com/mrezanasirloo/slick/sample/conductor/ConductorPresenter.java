package com.mrezanasirloo.slick.sample.conductor;

import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public class ConductorPresenter extends SlickPresenter<ConductorView> {

    private static final String TAG = ConductorPresenter.class.getSimpleName();

    @Override
    public void onViewUp(ConductorView view) {
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
