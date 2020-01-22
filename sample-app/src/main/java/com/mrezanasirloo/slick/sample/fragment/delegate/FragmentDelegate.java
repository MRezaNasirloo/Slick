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

package com.mrezanasirloo.slick.sample.fragment.delegate;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.R;

import androidx.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDelegate extends Fragment implements ViewFragmentDelegate {

    @Presenter
    PresenterFragmentDelegate presenter;

    public FragmentDelegate() {
        // Required empty public constructor
    }

    public static FragmentDelegate newInstance() {
        return new FragmentDelegate();
    }

    @Nullable
    @Override
    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_example, container, false);
        ((TextView) view.findViewById(R.id.text_view_fragment)).setText("Delegate Fragment without base class");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        PresenterFragmentDelegate_Slick.bind(this, 1, "2");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        PresenterFragmentDelegate_Slick.onStart(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        PresenterFragmentDelegate_Slick.onStop(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        PresenterFragmentDelegate_Slick.onDestroy(this);
        super.onDestroy();
    }


}
