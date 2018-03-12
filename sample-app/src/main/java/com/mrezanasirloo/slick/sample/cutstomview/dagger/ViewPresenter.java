package com.mrezanasirloo.slick.sample.cutstomview.dagger;

import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;

import javax.inject.Inject;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-09
 */

public class ViewPresenter extends SlickPresenter<ExampleView> {

    private static final String TAG = ViewPresenter.class.getSimpleName();

    @Inject
    public ViewPresenter() {
    }

    public String getData() {
        return "Text data has received from presenter";
    }

    @Override
    public void onViewUp(ExampleView view) {
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
