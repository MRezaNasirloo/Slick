package com.mrezanasirloo.slick.sample.conductor.dagger;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;

import javax.inject.Inject;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public class ConductorPresenter extends SlickPresenter<ConductorView> {

    private static final String TAG = ConductorPresenter.class.getSimpleName();
    @NonNull
    private final Long lng;
    private final String s;

    @Inject
    public ConductorPresenter(@NonNull Long lng, String s) {
        this.lng = lng;
        this.s = s;
    }
    @Override
    public void onViewUp(ConductorView view) {
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
