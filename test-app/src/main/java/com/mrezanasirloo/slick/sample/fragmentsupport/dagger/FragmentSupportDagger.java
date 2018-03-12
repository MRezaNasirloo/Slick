package com.mrezanasirloo.slick.sample.fragmentsupport.dagger;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.App;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSupportDagger extends Fragment implements ViewFragmentSupportDagger {

    @Inject
    Provider<PresenterFragmentSupportDagger> provider;
    @Presenter
    PresenterFragmentSupportDagger presenter;

    public FragmentSupportDagger() {
        // Required empty public constructor
    }

    public static FragmentSupportDagger newInstance() {
        return new FragmentSupportDagger();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_example, container, false);
        ((TextView) view.findViewById(R.id.text_view_fragment)).setText("Dagger Fragment.");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getDaggerComponent(getActivity()).inject(this);
        PresenterFragmentSupportDagger_Slick.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) {
            App.disposeDaggerComponent(getActivity());
        }
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
