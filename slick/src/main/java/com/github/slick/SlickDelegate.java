package com.github.slick;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import java.util.UUID;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegate<V extends SlickView, P extends SlickPresenter<V>>
        implements Application.ActivityLifecycleCallbacks {

    private String id;
    private OnDestroyListener listener;

    private P presenter;
    private Class<? extends Activity> cls;
    private String slick_intent_key;
    private static String SLICK_INTENT_KEY = "SLICK_INTENT_KEY";
    private boolean multiInstance = false;


    public void bind(P presenterInstance, Class<? extends Activity> cls) {
        if (presenterInstance == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenterInstance;
        this.cls = cls;

    }

    public SlickDelegate() {
    }

    public SlickDelegate(P presenter, Class<? extends Activity> cls, String id) {
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != null) multiInstance = true;
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
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
            presenter.onDestroy();
            if (listener != null) {
                listener.onDestroy(id);
            }
            presenter = null;
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
        if (cls.isInstance(activity) && multiInstance) {
            if (activity.getIntent().getStringExtra("KEY").equals(this.id)) {
                presenter.onViewUp((V) activity);
            }
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
        if (cls.isInstance(activity) && multiInstance) {
            if (activity.getIntent().getStringExtra("KEY").equals(this.id)) {
                presenter.onViewDown();
            }
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
        if (cls.isInstance(activity) && multiInstance) {
            if (activity.getIntent().getStringExtra("KEY").equals(this.id)) {
                onDestroy((V) activity);
            }
        }
        Log.d(TAG, "onActivityDestroyed() called with " + activity.toString());
    }

    public void setListener(OnDestroyListener listener) {
        this.listener = listener;
    }

    public void onDestroyForViews(V view) {
        onDestroy(view);
    }

    public static String getActivityId(Activity activity) {
        final Intent intent = activity.getIntent();
        if (intent.hasExtra(SLICK_INTENT_KEY)) {
            return intent.getStringExtra(SLICK_INTENT_KEY);
        } else {
            String id = UUID.randomUUID().toString();
            intent.putExtra(SLICK_INTENT_KEY, id);
            activity.setIntent(intent);
            return id;
        }
    }
}
