package com.mrezanasirloo.slick.sample.cutstomview;

import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-09
 */

public class PresenterCustomView extends SlickPresenterTestable<ViewCustomView> {

    private static final String TAG = PresenterCustomView.class.getSimpleName();

    public String getData() {
        return "Text data has received from presenter";
    }

    @Override
    public void onViewUp(ViewCustomView view) {
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
