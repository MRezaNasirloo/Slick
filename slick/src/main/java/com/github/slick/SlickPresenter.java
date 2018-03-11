package com.github.slick;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-10-31
 *
 *         A dead simple Presenter with only 3 lifecycle callbacks
 */
public class SlickPresenter<V> {

    private WeakReference<V> view;

    /**
     * Lifecycle method
     * <p>
     * Is called when the view is in onStart phase
     *
     * @param view the view which has bound to this presenter
     */
    public void onViewUp(V view) {
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
