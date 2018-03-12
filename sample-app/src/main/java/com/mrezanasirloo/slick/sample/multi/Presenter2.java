package com.mrezanasirloo.slick.sample.multi;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;

import java.util.Locale;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public class Presenter2 extends SlickPresenter<View2> {

    private static final String TAG = Presenter2.class.getSimpleName();
    private final String s;

    public Presenter2(String s) {
        this.s = s;
    }

    @NonNull
    public String getData() {
        return String.format(Locale.ENGLISH, "Presenter2 text: %s", s);
    }

    @Override
    public void onViewUp(View2 view) {
        Log.d(TAG, "onViewUp() called");
        super.onViewUp(view);
        view.setText2(getData());
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
