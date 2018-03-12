package com.mrezanasirloo.slick.sample.multi;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;

import java.util.Locale;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public class Presenter1 extends SlickPresenter<View1> {

    private static final String TAG = Presenter1.class.getSimpleName();
    @NonNull private final Integer integer;
    private final String s;

    public Presenter1(@IdRes @NonNull Integer integer, String s) {
        this.integer = integer;
        this.s = s;
    }

    @NonNull
    public String getData() {
        return String.format(Locale.ENGLISH, "Presenter1 Random num: %d, text: %s", integer, s);
    }

    @Override
    public void onViewUp(View1 view) {
        Log.d(TAG, "onViewUp() called");
        super.onViewUp(view);
        view.setText1(getData());
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
