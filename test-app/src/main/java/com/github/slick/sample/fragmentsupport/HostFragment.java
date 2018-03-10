package com.github.slick.sample.fragmentsupport;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.slick.sample.R;
import com.github.slick.sample.fragmentsupport.dagger.FragmentSupportDagger;
import com.github.slick.sample.fragmentsupport.simple.FragmentSupport;

public class HostFragment extends Fragment {

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
                        .replace(R.id.fragment, FragmentSupport.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        view.findViewById(R.id.button_fragment_support_dagger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, FragmentSupportDagger.newInstance())
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
