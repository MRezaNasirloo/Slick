package com.mrezanasirloo.slick.sample.conductor;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-13
 */

public class ControllerSimple extends Controller implements ViewConductor {

    @Presenter
    PresenterConductor presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        PresenterConductor_Slick.bind(this);
        return inflater.inflate(R.layout.home_layout, container, false);
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}