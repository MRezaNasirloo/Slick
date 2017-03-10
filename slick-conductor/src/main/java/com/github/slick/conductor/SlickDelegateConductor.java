package com.github.slick.conductor;

import android.support.annotation.NonNull;
import android.view.View;

import com.bluelinelabs.conductor.Controller;
import com.github.slick.OnDestroyListener;
import com.github.slick.SlickPresenter;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateConductor<V, P extends SlickPresenter<V>>
        extends Controller.LifecycleListener {
    private static final String TAG = SlickDelegateConductor.class.getSimpleName();

    private String id;
    private OnDestroyListener listener;

    private P presenter;
    private Class<? extends Controller> cls;
    private boolean multiInstance = false;

    public SlickDelegateConductor(P presenter, Class<? extends Controller> cls, String id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != null) multiInstance = true;
    }

    public void onDestroy(Controller controller) {
        controller.removeLifecycleListener(this);
        presenter.onDestroy();
        if (listener != null) {
            listener.onDestroy(id);
        }
        presenter = null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void postAttach(@NonNull Controller controller, @NonNull View view) {
        if (multiInstance) {
            if (isSameInstance(controller)) {
                presenter.onViewUp((V) controller);
            }
        } else if (cls.isInstance(controller)) {
            presenter.onViewUp((V) controller);
        }
    }

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

    public void setListener(OnDestroyListener listener) {
        this.listener = listener;
    }

    public P getPresenter() {
        return presenter;
    }

    private boolean isSameInstance(Controller controller) {
        return controller.getInstanceId().equals(this.id);
    }

}
