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


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.Slick;
import com.mrezanasirloo.slick.SlickFragment;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.fragment.dagger.DaggerFragment;
import com.mrezanasirloo.slick.sample.fragment.dagger.delegate.DelegateDaggerFragment;
import com.mrezanasirloo.slick.sample.fragment.delegate.DelegateFragment;

public class SimpleSlickFragment extends SlickFragment<SimpleFragmentView, SimpleFragmentPresenter>
        implements SimpleFragmentView {

    @Presenter
    SimpleFragmentPresenter presenter;

    public SimpleSlickFragment() {
        // Required empty public constructor
    }

    public static SimpleSlickFragment newInstance() {
        return new SimpleSlickFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_simple, container, false);

        view.findViewById(R.id.button_delegate_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, DelegateFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.button_dagger_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, DaggerFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.button_delegate_dagger_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, DelegateDaggerFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    @Override
    protected Object bind() {
//        return SimpleSlickFragment_Slick.bind(this, 1, "2");
        return Slick.bind(this, 1, "2");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        setRetainInstance(true);
        Log.d(TAG, "onCreate() called");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach(context) called");
        super.onAttach(context);
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(TAG, "onAttach(activity) called");
        super.onAttach(activity);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        Log.d(TAG, "onAttachFragment() called");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart() called");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume() called");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause() called");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop() called");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView() called");
        super.onDestroyView();
    }

    private static final String TAG = SimpleSlickFragment.class.getSimpleName();
}
