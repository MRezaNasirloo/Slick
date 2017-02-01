package com.github.slick;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegate<V extends SlickView, P extends SlickPresenter<V>>
        implements Application.ActivityLifecycleCallbacks {

    private OnDestroyListener listener;

    private P presenter;
    private Class<? extends Activity> cls;

    public void bind(P presenterInstance, Class<? extends Activity> cls) {
        if (presenterInstance == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenterInstance;
        this.cls = cls;

    }

    public P onCreate(P presenter) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        return this.presenter = presenter;
    }

    public void onStart(V view) {
        presenter.onViewUp(view);
    }

    public void onStop(V view) {
        presenter.onViewDown();
    }

    public void onDestroy(V view) {
        Activity activity;
        if (view instanceof View) {
            activity = ((Activity) ((View) view).getContext());
        } else if (view instanceof Fragment/* && ((Fragment) view).getParentFragment() == null*/) {
            activity = ((Fragment) view).getActivity();
        } else if (view instanceof Activity) {
            activity = ((Activity) view);
        } else {
            return;
        }
        if (!activity.isChangingConfigurations()) {
            presenter.onDestroy();
            if (listener != null) {
                listener.onDestroy();
            }
        }
        //else if (view instanceof android.app.Fragment && ((android.app.Fragment) view).getParentFragment() == null) activity = ((android.app.Fragment) view).getActivity();
    }

    /**
     * For use in custom views only
     *
     * @param view the view interface
     */
    public void onAttachedToWindow(V view) {
        presenter.onViewUp(view);
    }

    /**
     * For use in custom views only
     */
    public void onDetachedFromWindow(V view) {
        presenter.onViewDown();
        if (((Activity) ((View) view).getContext()).isFinishing()) {
            presenter.onDestroy();
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //no-op
    }

    private static final String TAG = SlickDelegate.class.getSimpleName();

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityStarted(Activity activity) {
        if (cls.isInstance(activity)) {
            presenter.onViewUp((V) activity);
            Log.d(TAG, "onActivityStarted() called with " + activity.toString());

        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        //no-op
    }

    @Override
    public void onActivityPaused(Activity activity) {
        //no-op
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityStopped(Activity activity) {
        if (cls.isInstance(activity)) {
            presenter.onViewDown();
        }
        Log.d(TAG, "onActivityStopped() called with " + activity.toString());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //no-op
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityDestroyed(Activity activity) {
        if (cls.isInstance(activity)) {
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
            onDestroy((V) activity);
        }
        Log.d(TAG, "onActivityDestroyed() called with " + activity.toString());
    }

    public void setListener(OnDestroyListener listener) {
        this.listener = listener;
    }

    public void onDestroyForViews(V view) {
        onDestroy(view);
    }
}
