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

package com.mrezanasirloo.slick;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-10-31
 *
 *         A dead simple Presenter with only 3 lifecycle callbacks
 */
public class SlickPresenter<V> {

    @Nullable
    private WeakReference<V> view;

    /**
     * Lifecycle method
     * <p>
     * Is called when the view is in onStart phase
     *
     * @param view the view which has bound to this presenter
     */
    public void onViewUp(@NonNull V view) {
        this.view = new WeakReference<>(view);
    }

    /**
     * Lifecycle method
     *
     * Is called when the view is in onStop phase
     */
    public void onViewDown() {

    }

    /**
     * Lifecycle method
     *
     * Is called when the view is finishing
     */
    public void onDestroy() {
        if (view != null) {
            view.clear();
            view = null;
        }
    }

    /**
     * @return The view which has bounded to this presenter and is attached to screen
     */
    @Nullable
    protected V getView() {
        return view == null ? null : view.get();
    }
}
