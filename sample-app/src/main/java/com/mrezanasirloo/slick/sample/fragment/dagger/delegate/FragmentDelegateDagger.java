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

package com.mrezanasirloo.slick.sample.fragment.dagger.delegate;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.App;
import com.mrezanasirloo.slick.sample.R;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDelegateDagger extends Fragment implements ViewFragmentDelegateDagger {

    @Inject
    Provider<PresenterFragmentDelegateDagger> provider;
    @Presenter
    PresenterFragmentDelegateDagger presenter;


    public FragmentDelegateDagger() {
        // Required empty public constructor
    }

    public static FragmentDelegateDagger newInstance() {
        return new FragmentDelegateDagger();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_example, container, false);
        ((TextView) view.findViewById(R.id.text_view_fragment)).setText("This Fragment uses delegation to send callbacks to Presenter");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getDaggerComponent(getActivity()).inject(this);
        PresenterFragmentDelegateDagger_Slick.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        PresenterFragmentDelegateDagger_Slick.onStart(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        PresenterFragmentDelegateDagger_Slick.onStop(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PresenterFragmentDelegateDagger_Slick.onDestroy(this);
        if (getActivity().isFinishing()) {
            App.disposeDaggerComponent(getActivity());
        }
    }


}
