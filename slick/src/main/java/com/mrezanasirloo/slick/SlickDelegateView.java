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
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.Locale;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 * Created on: 2016-11-03
 */

public class SlickDelegateView<V, P extends SlickPresenter<V>> {

    private int id;
    private InternalOnDestroyListener listener;

    @Nullable
    private P presenter;
    /**
     * to ensure not to call onViewDown after onDestroy
     */
    private boolean hasOnViewDownCalled = false;

    public SlickDelegateView(@Nullable P presenter, @Nullable Class ignored, int id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.id = id;
    }

    private static final String TAG = SlickDelegateView.class.getSimpleName();

    @SuppressWarnings("ConstantConditions")
    public void onAttach(@NonNull V view) {
        presenter.onViewUp(view);
        hasOnViewDownCalled = false;
    }

    @SuppressWarnings("ConstantConditions")
    public void onDetach(@Nullable Object ignored) {
        presenter.onViewDown();
        hasOnViewDownCalled = true;
    }

    public void onDestroy(@Nullable V view) {
        destroy(getActivity((View) view));
    }

    public void onDestroy(@Nullable Activity activity) {
        destroy(activity);
    }

    private void destroy(@Nullable Activity activity) {
        if (activity == null || !activity.isChangingConfigurations()) {
            if (!hasOnViewDownCalled) onDetach(null);
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

    public void setListener(@NonNull InternalOnDestroyListener listener) {
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

    @Nullable
    public static Activity getActivity(@Nullable View view) {
        if (view == null) return null;
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        throw new IllegalStateException("Cannot find the activity, Are you sure the supplied view was attached?");
    }

}
