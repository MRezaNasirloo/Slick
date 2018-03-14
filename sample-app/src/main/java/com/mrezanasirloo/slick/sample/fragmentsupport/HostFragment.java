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

package com.mrezanasirloo.slick.sample.fragmentsupport;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.fragment.SimpleFragmentView;
import com.mrezanasirloo.slick.sample.fragmentsupport.dagger.DaggerFragmentSupport;
import com.mrezanasirloo.slick.sample.fragmentsupport.simple.SupportFragment;

public class HostFragment extends Fragment implements SimpleFragmentView {

    public HostFragment() {
        // Required empty public constructor
    }

    public static HostFragment newInstance() {
        return new HostFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_support_simple, container, false);

        view.findViewById(R.id.button_fragment_support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, SupportFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.button_fragment_support_dagger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, DaggerFragmentSupport.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        System.out.println("HostFragment.onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        System.out.println("HostFragment.onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        System.out.println("HostFragment.onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        System.out.println("HostFragment.onDestroyView");
        System.out.println("HostFragment.onDestroyView is Removing: " + isRemoving());
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        System.out.println("HostFragment.onDetach");
        super.onDetach();
    }
}
