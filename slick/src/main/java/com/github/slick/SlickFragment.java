package com.github.slick;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-07
 */

public abstract class SlickFragment<V extends SlickView, P extends SlickPresenter<V>> extends Fragment implements SlickDelegator {

    protected P presenter;
    private final SlickDelegate<V, P> delegate = new SlickDelegate<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = delegate.onCreate(getPresenter());
    }

    protected abstract P getPresenter();

    @Override
    @SuppressWarnings("unchecked")
    public void onStart() {
        super.onStart();
        delegate.onStart((V)this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onStop() {
        super.onStop();
        delegate.onStop((V)this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onDestroy() {
        delegate.onDestroy((V) this);
        super.onDestroy();
    }

    public SlickDelegate getSlickDelegate() {
        return delegate;
    }
}
