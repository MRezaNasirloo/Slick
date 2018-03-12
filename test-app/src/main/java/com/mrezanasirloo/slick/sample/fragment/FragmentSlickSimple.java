package com.mrezanasirloo.slick.sample.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.SlickFragment;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;
import com.mrezanasirloo.slick.sample.fragment.dagger.FragmentDagger;
import com.mrezanasirloo.slick.sample.fragment.dagger.delegate.FragmentDelegateDagger;
import com.mrezanasirloo.slick.sample.fragment.delegate.FragmentDelegate;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

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

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
