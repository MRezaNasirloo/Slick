package com.github.slick;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-10-31
 */
public abstract class SlickActivity<V extends SlickView, P extends SlickPresenter<V>>
        extends AppCompatActivity implements SlickView, SlickDelegator {

    protected P presenter;
    private SlickDelegate<V, P> delegate = new SlickDelegate<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = delegate.onCreate(getPresenter());
        if (presenter == null) {
            throw new IllegalStateException(
                    "Override SlickActivity#getPresenter to provide a presenter.");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onStart() {
        super.onStart();
        delegate.onStart((V) this);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onStop() {
        super.onStop();
        delegate.onStop((V) this);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onDestroy() {
        delegate.onDestroy((V) this);
        super.onDestroy();
    }

    @NonNull
    @Override
    public SlickDelegate<V, P> getSlickDelegate() {
        return delegate;
    }

    protected abstract P getPresenter();
}
