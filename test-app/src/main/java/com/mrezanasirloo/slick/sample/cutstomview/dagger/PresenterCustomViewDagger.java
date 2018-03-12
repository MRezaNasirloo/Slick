package com.mrezanasirloo.slick.sample.cutstomview.dagger;

import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import javax.inject.Inject;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-09
 */

public class PresenterCustomViewDagger extends SlickPresenterTestable<ViewCustomViewDagger> {

    private static final String TAG = PresenterCustomViewDagger.class.getSimpleName();

    @Inject
    public PresenterCustomViewDagger() {
    }

    public String getData() {
        return "Text data has received from presenter";
    }

    @Override
    public void onViewUp(ViewCustomViewDagger view) {
        super.onViewUp(view);
        Log.d(TAG, "onViewUp() called" + toString());
    }

    @Override
    public void onViewDown() {
        super.onViewDown();
        Log.d(TAG, "onViewDown() called" + toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called" + toString());
    }
}
