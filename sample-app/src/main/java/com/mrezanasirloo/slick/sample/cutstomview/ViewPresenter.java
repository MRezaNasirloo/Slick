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

package com.mrezanasirloo.slick.sample.cutstomview;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mrezanasirloo.slick.SlickPresenter;

import java.util.Random;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-09
 */

public class ViewPresenter extends SlickPresenter<ViewCustomView> {

    private static final String TAG = ViewPresenter.class.getSimpleName();
    private final int randomCode;

    public ViewPresenter() {
        randomCode = new Random().nextInt(100 - 1) + 1;
    }

    public int getCode() {
        return randomCode;
    }

    @Override
    public void onViewUp(@NonNull ViewCustomView view) {
        super.onViewUp(view);
        Log.d(TAG, "onViewUp() called: " + getCode());
    }

    @Override
    public void onViewDown() {
        super.onViewDown();
        Log.d(TAG, "onViewDown() called: " + getCode());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called: " + getCode());
    }
}
