package com.github.slick.sample.conductor.dagger;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.github.slick.Presenter;
import com.github.slick.sample.App;
import com.github.slick.sample.R;
import com.github.slick.Slick;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-13
 */

public class ExampleController extends Controller implements ConductorView {

    @Inject
    Provider<ConductorPresenter> provider;
    @Presenter
    ConductorPresenter presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        App.getDaggerComponent(getApplicationContext()).inject(this);
        Slick.bind(this);
        return inflater.inflate(R.layout.home_layout, container, false);
    }

    @Override
    protected void onDestroy() {
        App.disposeDaggerComponent(getApplicationContext());
    }
}