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

package com.mrezanasirloo.slick.sample.conductor.dagger;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.App;
import com.mrezanasirloo.slick.sample.R;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;

/**
 * @author : M.Reza.Nasirloo@gmail.com
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
        return inflater.inflate(R.layout.contoller_home, container, false);
    }

    @Override
    protected void onDestroy() {
        App.disposeDaggerComponent(getApplicationContext());
    }


}