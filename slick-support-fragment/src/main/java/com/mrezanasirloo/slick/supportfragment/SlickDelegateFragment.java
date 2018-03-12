package com.mrezanasirloo.slick.supportfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks;

import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.SlickPresenter;
import com.mrezanasirloo.slick.SlickUniqueId;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */

public class SlickDelegateFragment<V, P extends SlickPresenter<V>> extends FragmentLifecycleCallbacks {

    private int id;
    private InternalOnDestroyListener listener;

    private P presenter;
    private Class cls;
    private boolean multiInstance = false;

    public SlickDelegateFragment(P presenter, Class cls, int id) {
        if (presenter == null) {
            throw new IllegalStateException("Presenter cannot be null.");
        }
        this.presenter = presenter;
        this.cls = cls;
        this.id = id;
        if (id != -1) multiInstance = true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onFragmentStarted(FragmentManager fm, Fragment fragment) {
        if (multiInstance) {
            if (isSameInstance(fragment)) {
                presenter.onViewUp((V) fragment);
            }

        } else if (cls.isInstance(fragment)) {
            presenter.onViewUp((V) fragment);
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment fragment) {
        if (multiInstance) {
            if (isSameInstance(fragment)) {
                presenter.onViewDown();
            }
        } else if (cls.isInstance(fragment)) {
            presenter.onViewDown();
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment fragment) {
        if (multiInstance) {
            if (isSameInstance(fragment)) {
                destroy(fragment);
            }
        } else if (cls.isInstance(fragment)) {
            destroy(fragment);
        }
    }

    private void destroy(Fragment fragment) {
        if (!fragment.getActivity().isChangingConfigurations()) {
            fragment.getFragmentManager().unregisterFragmentLifecycleCallbacks(this);
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
}
