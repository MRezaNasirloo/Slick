package com.github.slick.sample.fragment.delegate;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.slick.SlickPresenter;
import com.github.slick.test.SlickPresenterTestable;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class PresenterFragmentDelegate extends SlickPresenterTestable<ViewFragmentDelegate> {

    private static final String TAG = PresenterFragmentDelegate.class.getSimpleName();

    public PresenterFragmentDelegate(@NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(ViewFragmentDelegate view) {
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
