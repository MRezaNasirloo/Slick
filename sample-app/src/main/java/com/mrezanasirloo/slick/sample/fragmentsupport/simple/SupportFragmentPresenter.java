package com.mrezanasirloo.slick.sample.fragmentsupport.simple;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public class SupportFragmentPresenter extends SlickPresenter<SupportFragmentView> {

    private static final String TAG = SupportFragmentPresenter.class.getSimpleName();

    public SupportFragmentPresenter(@NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(SupportFragmentView view) {
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
