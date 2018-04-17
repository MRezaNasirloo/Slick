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

package com.mrezanasirloo.slick.test;

import android.support.annotation.NonNull;

import com.mrezanasirloo.slick.SlickPresenter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-10
 */

public class SlickPresenterTestable<V> extends SlickPresenter<V> {

    private AtomicInteger onViewUpCount = new AtomicInteger();
    private AtomicInteger onViewDownCount = new AtomicInteger();
    private AtomicInteger onDestroyCount = new AtomicInteger();

    @Override
    public void onViewUp(@NonNull V view) {
        super.onViewUp(view);
        onViewUpCount.getAndIncrement();
    }

    @Override
    public void onViewDown() {
        super.onViewDown();
        onViewDownCount.getAndIncrement();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroyCount.getAndIncrement();
    }

    public int onViewUpCount() {
        return onViewUpCount.get();
    }

    public int onViewDownCount() {
        return onViewDownCount.get();
    }

    public int onDestroyCount() {
        return onDestroyCount.get();
    }
}
