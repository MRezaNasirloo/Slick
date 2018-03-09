package com.github.slick.sample.activity;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.slick.SlickPresenter;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class ExampleActivityPresenter extends SlickPresenter<ExampleActivityView> {

    private static final String TAG = ExampleActivityPresenter.class.getSimpleName();

    public ExampleActivityPresenter(@IdRes @NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(ExampleActivityView view) {
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
