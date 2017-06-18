package com.github.slick;

import android.app.Activity;
import android.view.View;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateView<V, P extends SlickPresenter<V>> {

    private int id;
    private OnDestroyListener listener;

    private P presenter;
    private Class cls;
    private boolean multiInstance = false;

    public SlickDelegateView(P presenter, Class cls, int id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != -1) multiInstance = true;
    }

    public void onAttach(V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                presenter.onViewUp(view);
            }

        } else if (cls.isInstance(view)) {
            presenter.onViewUp(view);
        }
    }

    public void onDetach(V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                presenter.onViewDown();
                destroy(view);
            }
        } else if (cls.isInstance(view)) {
            presenter.onViewDown();
            destroy(view);
        }
    }

    private void destroy(V view) {
        final Activity activity = (Activity) ((View) view).getContext();
        if (!activity.isChangingConfigurations()) {
            presenter.onDestroy();
            if (listener != null) {
                listener.onDestroy(id);
            }
            presenter = null;
        }
    }

    public P getPresenter() {
        return presenter;
    }

    public void setListener(OnDestroyListener listener) {
        this.listener = listener;
    }


    public static int getId(Object view) {
        if (view instanceof SlickUniqueId) return ((SlickUniqueId) view).getUniqueId().hashCode();
        return -1;
    }

    private boolean isSameInstance(Object view) {
        final int id = getId(view);
        return id != -1 && id == this.id;
    }

}
