/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick.sample.multipresenter;

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
