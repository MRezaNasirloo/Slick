package com.github.slick.sample.fragmentsupport;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.slick.sample.R;
import com.github.slick.sample.fragment.SimpleFragmentView;
import com.github.slick.sample.fragmentsupport.dagger.DaggerFragmentSupport;
import com.github.slick.sample.fragmentsupport.simple.SupportFragment;

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
}
