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
import android.view.View;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateView<V, P extends SlickPresenter<V>> {

    private int id;
    private InternalOnDestroyListener listener;

    private P presenter;
    private Class cls;
    private boolean multiInstance = false;
    /**
     * to ensure not to call onViewDown after onDestroy
     */
    private boolean hasOnViewDownCalled = false;

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
                hasOnViewDownCalled = false;
            }

        } else if (cls.isInstance(view)) {
            presenter.onViewUp(view);
            hasOnViewDownCalled = false;
        }
    }

    public void onDetach(V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                presenter.onViewDown();
                hasOnViewDownCalled = true;
            }
        } else if (cls.isInstance(view)) {
            presenter.onViewDown();
            hasOnViewDownCalled = true;
        }
    }

    public void onDestroy(V view) {
        if (multiInstance) {
            if (isSameInstance(view)) {
                destroy(view);
            }
        } else if (cls.isInstance(view)) {
            destroy(view);
        }
    }

    private void destroy(V view) {
        final Activity activity = getActivity((View) view);
        if (!activity.isChangingConfigurations()) {
            if (!hasOnViewDownCalled) onDetach(view);
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

    public void setListener(InternalOnDestroyListener listener) {
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

    public Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
