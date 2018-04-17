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

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Locale;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateFragment<V, P extends SlickPresenter<V>> {

    private int id;
    private InternalOnDestroyListener listener;

    @Nullable
    private P presenter;
    private Class cls;
    private boolean multiInstance = false;

    public SlickDelegateFragment(@Nullable P presenter, Class cls, int id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != -1) multiInstance = true;
    }

    public SlickDelegateFragment(@Nullable P presenter, Class cls) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
    }

    @SuppressWarnings("ConstantConditions")
    public void onStart(@NonNull V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                presenter.onViewUp(view);
            }

        } else if (cls.isInstance(view)) {
            presenter.onViewUp(view);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void onStop(@NonNull V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                presenter.onViewDown();
            }
        } else if (cls.isInstance(view)) {
            presenter.onViewDown();
        }
    }

    public void onDestroy(@NonNull V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                destroy(view);
            }
        } else if (cls.isInstance(view)) {
            destroy(view);
        }
    }

    private void destroy(@NonNull V view) {
        Activity activity;
        if (view instanceof Fragment) {
            activity = ((Fragment) view).getActivity();
        } else {
            throw new IllegalStateException(
                    "View should be either a subclass of support Fragment or android.app.Fragment");
        }
        if (!activity.isChangingConfigurations()) {
            //noinspection ConstantConditions
            presenter.onDestroy();
            if (listener != null) {
                listener.onDestroy(id);
            }
            presenter = null;
        }
    }

    @Nullable
    public P getPresenter() {
        return presenter;
    }

    public void setListener(InternalOnDestroyListener listener) {
        this.listener = listener;
    }


    @SuppressWarnings("ConstantConditions")
    public static int getId(@NonNull Object view) {
        if (view == null) throw new NullPointerException("Cannot get an Id from a null view." +
                " Are you sure you call the delegate methods at the right place?");
        if (view instanceof SlickUniqueId) {
            String uniqueId = ((SlickUniqueId) view).getUniqueId();
            if (uniqueId == null) throw new IllegalStateException(String.format(Locale.ENGLISH,
                    "Your View: %s has implemented SlickUniqueId but instead of returning an Id it returned null", view.getClass().toString()));
            return uniqueId.hashCode();
        }
        return -1;
    }

    private boolean isSameInstance(@NonNull Object view) {
        final int id = getId(view);
        return id != -1 && id == this.id;
    }
}
