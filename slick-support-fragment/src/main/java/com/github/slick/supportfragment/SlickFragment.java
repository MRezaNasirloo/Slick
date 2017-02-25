package com.github.slick.supportfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.slick.SlickFragmentDelegate;
import com.github.slick.SlickPresenter;
import com.github.slick.SlickUniqueId;

import java.util.UUID;

import android.support.annotation.Nullable;

import static com.github.slick.SlickDelegate.SLICK_UNIQUE_KEY;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-07
 */

public abstract class SlickFragment<V, P extends SlickPresenter<V>> extends Fragment
        implements SlickUniqueId {

    private SlickFragmentDelegate<V, P> delegate;
    private String id;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            id = savedInstanceState.getString(SLICK_UNIQUE_KEY);
        }
        delegate = bind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SLICK_UNIQUE_KEY, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onStart() {
        delegate.onStart((V) this);
        super.onStart();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onStop() {
        delegate.onStop((V) this);
        super.onStop();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onDestroy() {
        delegate.onDestroy((V) this);
        super.onDestroy();
    }

    @Override
    public String getUniqueId() {
        return id = id != null ? id : UUID.randomUUID().toString();
    }

    protected abstract SlickFragmentDelegate<V, P> bind();
}
