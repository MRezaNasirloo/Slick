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

package com.mrezanasirloo.slick.conductor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bluelinelabs.conductor.Controller;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.SlickPresenter;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateConductor<V, P extends SlickPresenter<V>>
        extends Controller.LifecycleListener {
    private static final String TAG = SlickDelegateConductor.class.getSimpleName();

    private int id;
    private InternalOnDestroyListener listener;

    @Nullable
    private P presenter;
    private Class<? extends Controller> cls;
    private boolean multiInstance = false;

    public SlickDelegateConductor(@Nullable P presenter, Class<? extends Controller> cls, int id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != -1) multiInstance = true;
    }

    public void onDestroy(@NonNull Controller controller) {
        controller.removeLifecycleListener(this);
        //noinspection ConstantConditions
        presenter.onDestroy();
        if (listener != null) {
            listener.onDestroy(id);
        }
        presenter = null;
    }

    @Override
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    public void postAttach(@NonNull Controller controller, @NonNull View view) {
        if (multiInstance) {
            if (isSameInstance(controller)) {
                presenter.onViewUp((V) controller);
            }
        } else if (cls.isInstance(controller)) {
            presenter.onViewUp((V) controller);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void postDetach(@NonNull Controller controller, @NonNull View view) {
        if (multiInstance) {
            if (isSameInstance(controller)) {
                presenter.onViewDown();
            }
        } else if (cls.isInstance(controller)) {
            presenter.onViewDown();
        }
    }

    @Override
    public void postDestroy(@NonNull Controller controller) {
        if (multiInstance) {
            if (isSameInstance(controller)) {
                onDestroy(controller);
            }
        } else if (cls.isInstance(controller)) {
            onDestroy(controller);
        }
    }

    public void setListener(InternalOnDestroyListener listener) {
        this.listener = listener;
    }

    @Nullable
    public P getPresenter() {
        return presenter;
    }

    private boolean isSameInstance(@NonNull Controller controller) {
        return controller.getInstanceId().hashCode() == this.id;
    }

}
