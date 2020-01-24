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

package com.mrezanasirloo.slick.sample.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.SlickFragment;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.fragment.dagger.FragmentDagger;
import com.mrezanasirloo.slick.sample.fragment.dagger.delegate.FragmentDelegateDagger;
import com.mrezanasirloo.slick.sample.fragment.delegate.FragmentDelegate;

import androidx.annotation.Nullable;


public class FragmentSlickSimple extends SlickFragment<ViewFragmentSimple, PresenterFragmentSimple> implements ViewFragmentSimple {

    private static final String TAG = FragmentSlickSimple.class.getSimpleName();

    @Presenter
    PresenterFragmentSimple presenter;

    public FragmentSlickSimple() {
        // Required empty public constructor
    }

    public static FragmentSlickSimple newInstance() {
        return new FragmentSlickSimple();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_simple, container, false);

        view.findViewById(R.id.button_delegate_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, FragmentDelegate.newInstance(), "FragmentDelegate")
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.button_dagger_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, FragmentDagger.newInstance(), "DaggerFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.button_delegate_dagger_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, FragmentDelegateDagger.newInstance(), "DelegateDaggerFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    @Override
    protected Object bind() {
        return PresenterFragmentSimple_Slick.bind(this, 1, "2");
        // return Slick.bind(this, 1, "2");
    }


}
