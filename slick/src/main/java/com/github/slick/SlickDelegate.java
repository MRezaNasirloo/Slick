package com.github.slick;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import java.util.UUID;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegate<V, P extends SlickPresenter<V>>
        implements Application.ActivityLifecycleCallbacks {

    private String id;
    private OnDestroyListener listener;

    private P presenter;
    private Class<? extends Activity> cls;
    private boolean multiInstance = false;

    static String SLICK_UNIQUE_KEY = "SLICK_UNIQUE_KEY";

    public SlickDelegate() {
    }

    public SlickDelegate(P presenter, Class<? extends Activity> cls, String id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != null) multiInstance = true;
    }

    public SlickDelegate(P presenter, Class<? extends Activity> cls) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
    }

    public void onStart(V view) {
        presenter.onViewUp(view);
    }

    public void onStop(V view) {
        presenter.onViewDown();
    }

    public void onDestroy(V view) {
        Activity activity;
        if (view instanceof Activity) {
            activity = ((Activity) view);
        } else {
            throw new IllegalStateException(
                    "View should be a subclass of android.app.Activity or its subclasses.");
        }
        if (!activity.isChangingConfigurations()) {
            activity.getApplication().unregisterActivityLifecycleCallbacks(this);
            presenter.onDestroy();
            if (listener != null) {
                listener.onDestroy(id);
            }
            presenter = null;
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
        if (multiInstance) {
            if (isSameInstance(activity)) {
                presenter.onViewUp((V) activity);
            }

        } else if (cls.isInstance(activity)) {
            presenter.onViewUp((V) activity);
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
        if (multiInstance) {
            if (isSameInstance(activity)) {
                presenter.onViewDown();
            }
        } else if (cls.isInstance(activity)) {
            presenter.onViewDown();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        //no-op
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onActivityDestroyed(Activity activity) {
        if (multiInstance) {
            if (isSameInstance(activity)) {
                onDestroy((V) activity);
            }
        } else if (cls.isInstance(activity)) {
            onDestroy((V) activity);
        }
    }

    public void setListener(OnDestroyListener listener) {
        this.listener = listener;
    }

    public P getPresenter() {
        return presenter;
    }

    public static String getActivityId(Activity activity) {
        final Intent intent = activity.getIntent();
        if (intent.hasExtra(SLICK_UNIQUE_KEY)) {
            return intent.getStringExtra(SLICK_UNIQUE_KEY);
        } else {
            String id = UUID.randomUUID().toString();
            intent.putExtra(SLICK_UNIQUE_KEY, id);
            activity.setIntent(intent);
            return id;
        }
    }

    private boolean isSameInstance(Activity activity) {
        final String id = activity.getIntent().getStringExtra(SLICK_UNIQUE_KEY);
        return id != null && id.equals(this.id);
    }
}
