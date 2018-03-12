package com.mrezanasirloo.slick.sample.fragment.dagger;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.Slick;
import com.mrezanasirloo.slick.SlickFragment;
import com.mrezanasirloo.slick.sample.App;
import com.mrezanasirloo.slick.sample.R;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaggerFragment extends SlickFragment<DaggerFragmentView, DaggerFragmentPresenter>
        implements DaggerFragmentView {

    @Inject
    Provider<DaggerFragmentPresenter> presenterProvider;
    @Presenter
    DaggerFragmentPresenter presenter;

    public DaggerFragment() {
        // Required empty public constructor
    }

    public static DaggerFragment newInstance() {
        return new DaggerFragment();
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
        App.getDaggerComponent(getActivity()).inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Object bind() {
        return Slick.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) {
            App.disposeDaggerComponent(getActivity());
        }
    }
}
