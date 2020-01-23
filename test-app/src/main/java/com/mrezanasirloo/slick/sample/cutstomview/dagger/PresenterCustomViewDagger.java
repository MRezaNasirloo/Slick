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

package com.mrezanasirloo.slick.sample.cutstomview.dagger;


import android.util.Log;

import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import javax.inject.Inject;

import androidx.annotation.NonNull;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-09
 */

public class PresenterCustomViewDagger extends SlickPresenterTestable<ViewCustomViewDagger> {

    private static final String TAG = PresenterCustomViewDagger.class.getSimpleName();

    @Inject
    public PresenterCustomViewDagger() {
    }

    public String getData() {
        return "Text data has received from presenter";
    }

    @Override
    public void onViewUp(@NonNull ViewCustomViewDagger view) {
        super.onViewUp(view);
        Log.d(TAG, "onViewUp() called" + toString());
    }

    @Override
    public void onViewDown() {
        super.onViewDown();
        Log.d(TAG, "onViewDown() called" + toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called" + toString());
    }
}
