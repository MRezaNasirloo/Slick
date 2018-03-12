package com.mrezanasirloo.slick.sample.conductor.dagger;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.App;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-13
 */

public class ControllerDagger extends Controller implements ViewConductorDagger {

    @Inject
    Provider<PresenterConductorDagger> provider;
    @Presenter
    PresenterConductorDagger presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        App.getDaggerComponent(getApplicationContext()).inject(this);
        PresenterConductorDagger_Slick.bind(this);
        return inflater.inflate(R.layout.home_layout, container, false);
    }

    @Override
    protected void onDestroy() {
        App.disposeDaggerComponent(getApplicationContext());
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}