package com.mrezanasirloo.slick.sample.activity.dagger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;

import javax.inject.Inject;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public class ExampleActivityPresenter extends SlickPresenter<ExampleActivityView> {

    private static final String TAG = ExampleActivityPresenter.class.getSimpleName();
    @NonNull
    private final Long lng;
    private final String s;

    @Inject
    public ExampleActivityPresenter(@NonNull Long lng, String s) {
        this.lng = lng;
        this.s = s;
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
