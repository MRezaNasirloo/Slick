package com.github.slick.sample.conductor;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.github.slick.Presenter;
import com.github.slick.sample.R;
import com.github.slick.sample.Slick;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-13
 */

public class ExampleController extends Controller implements ConductorView {

    @Presenter
    ConductorPresenter presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        Slick.bind(this);
        return inflater.inflate(R.layout.home_layout, container, false);
    }

}