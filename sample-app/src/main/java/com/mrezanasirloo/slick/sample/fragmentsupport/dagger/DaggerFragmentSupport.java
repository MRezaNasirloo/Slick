package com.mrezanasirloo.slick.sample.fragmentsupport.dagger;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.Slick;
import com.mrezanasirloo.slick.sample.App;
import com.mrezanasirloo.slick.sample.R;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaggerFragmentSupport extends Fragment implements DaggerFragmentView {

    @Inject
    Provider<DaggerFragmentPresenter> provider;
    @Presenter
    DaggerFragmentPresenter presenter;

    public DaggerFragmentSupport() {
        // Required empty public constructor
    }

    public static DaggerFragmentSupport newInstance() {
        return new DaggerFragmentSupport();
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
        Slick.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) {
            App.disposeDaggerComponent(getActivity());
        }
    }
}
