package com.github.slick;

import android.support.annotation.Nullable;


import java.lang.ref.WeakReference;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-10-31
 */
public class SlickPresenter<V extends SlickView> {

    private WeakReference<V> view;

    public void onViewUp(V view) {
        this.view = new WeakReference<>(view);
    }

    public void onViewDown() {

    }

    public void onDestroy() {
        if (view != null) {
            view.clear();
            view = null;
        }
    }

    @Nullable
    public V getView() {
        return view == null ? null : view.get();
    }
}
