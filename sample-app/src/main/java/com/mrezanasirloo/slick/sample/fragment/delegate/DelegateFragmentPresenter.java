package com.mrezanasirloo.slick.sample.fragment.delegate;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public class DelegateFragmentPresenter extends SlickPresenter<DelegateFragmentView> {

    private static final String TAG = DelegateFragmentPresenter.class.getSimpleName();

    public DelegateFragmentPresenter(@NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(DelegateFragmentView view) {
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
