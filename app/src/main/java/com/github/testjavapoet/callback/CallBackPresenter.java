package com.github.testjavapoet.callback;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.slick.Presenter;
import com.github.slick.SlickPresenter;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
@Presenter(CallbackActivity.class)
public class CallBackPresenter extends SlickPresenter<CallBackView> {

    private static final String TAG = CallBackPresenter.class.getSimpleName();

    public CallBackPresenter(@IdRes @NonNull Integer integer, String s) {
    }

    @Override
    public void onViewUp(CallBackView view) {
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
