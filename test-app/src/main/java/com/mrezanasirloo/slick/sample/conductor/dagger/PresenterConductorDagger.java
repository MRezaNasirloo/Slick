package com.mrezanasirloo.slick.sample.conductor.dagger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import javax.inject.Inject;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public class PresenterConductorDagger extends SlickPresenterTestable<ViewConductorDagger> {

    private static final String TAG = PresenterConductorDagger.class.getSimpleName();
    @NonNull
    private final Long lng;
    private final String s;

    @Inject
    public PresenterConductorDagger(@NonNull Long lng, String s) {
        this.lng = lng;
        this.s = s;
    }
    @Override
    public void onViewUp(ViewConductorDagger view) {
        Log.d(TAG, "onViewUp() called hashCode: " + hashCode());
        super.onViewUp(view);
    }

    @Override
    public void onViewDown() {
        Log.d(TAG, "onViewDown() called hashCode: " + hashCode());
        super.onViewDown();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called hashCode: " + hashCode());
        super.onDestroy();
    }
}
