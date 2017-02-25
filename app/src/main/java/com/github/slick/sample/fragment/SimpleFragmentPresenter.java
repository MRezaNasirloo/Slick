package com.github.slick.sample.fragment;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.slick.SlickPresenter;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class SimpleFragmentPresenter extends SlickPresenter<SimpleFragmentView> {

    private static final String TAG = SimpleFragmentPresenter.class.getSimpleName();

    public SimpleFragmentPresenter(int integer, String s) {
    }

    @Override
    public void onViewUp(SimpleFragmentView view) {
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
