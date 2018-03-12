package com.mrezanasirloo.slick.sample.fragment.dagger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;

import javax.inject.Inject;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public class DaggerFragmentPresenter extends SlickPresenter<DaggerFragmentView> {

    private static final String TAG = DaggerFragmentPresenter.class.getSimpleName();

    @Inject
    public DaggerFragmentPresenter(@NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(DaggerFragmentView view) {
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
