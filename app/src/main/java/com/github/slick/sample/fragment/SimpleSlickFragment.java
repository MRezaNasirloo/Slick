package com.github.slick.sample.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.slick.Presenter;
import com.github.slick.SlickFragment;
import com.github.slick.sample.R;
import com.github.slick.Slick;
import com.github.slick.sample.fragment.dagger.DaggerFragment;
import com.github.slick.sample.fragment.dagger.delegate.DelegateDaggerSlickFragment;
import com.github.slick.sample.fragment.delegate.DelegateFragment;

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
                        .replace(R.id.fragment, DelegateDaggerSlickFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    @Override
    protected Object bind() {
        return Slick.bind(this, 1, "2");
    }
}
