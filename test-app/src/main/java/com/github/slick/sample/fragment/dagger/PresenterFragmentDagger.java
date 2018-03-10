package com.github.slick.sample.fragment.dagger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.slick.SlickPresenter;
import com.github.slick.test.SlickPresenterTestable;

import javax.inject.Inject;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class PresenterFragmentDagger extends SlickPresenterTestable<ViewFragmentDagger> {

    private static final String TAG = PresenterFragmentDagger.class.getSimpleName();

    @Inject
    public PresenterFragmentDagger(@NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(ViewFragmentDagger view) {
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
