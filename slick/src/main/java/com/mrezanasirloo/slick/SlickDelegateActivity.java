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
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateActivity<V, P extends SlickPresenter<V>> implements ActivityLifecycleCallbacks {

    private int id;
    private InternalOnDestroyListener listener;

    @Nullable
    private P presenter;
    private Class<? extends Activity> cls;
    private boolean multiInstance = false;

    public static String SLICK_UNIQUE_KEY = "SLICK_UNIQUE_KEY";

    public SlickDelegateActivity(@Nullable P presenter, Class<? extends Activity> cls, int id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != -1) multiInstance = true;
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
            //noinspection ConstantConditions
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

    @Override
    @SuppressWarnings({"unchecked", "ConstantConditions"})
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
    @SuppressWarnings({"unchecked", "ConstantConditions"})
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

    public void setListener(InternalOnDestroyListener listener) {
        this.listener = listener;
    }

    @Nullable
    public P getPresenter() {
        return presenter;
    }

    public static int getId(@NonNull Activity activity) {
        final Intent intent = activity.getIntent();
        if (intent.hasExtra(SLICK_UNIQUE_KEY)) {
            return intent.getIntExtra(SLICK_UNIQUE_KEY , -1);
        } else {
            int id = UUID.randomUUID().toString().hashCode();
            intent.putExtra(SLICK_UNIQUE_KEY, id);
            activity.setIntent(intent);
            return id;
        }
    }

    private boolean isSameInstance(@NonNull Activity activity) {
        final int id = activity.getIntent().getIntExtra(SLICK_UNIQUE_KEY, -1);
        return id != -1 && id == this.id;
    }
}
