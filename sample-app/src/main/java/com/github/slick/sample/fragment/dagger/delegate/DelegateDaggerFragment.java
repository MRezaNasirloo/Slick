package com.github.slick.sample.fragment.dagger.delegate;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.slick.Presenter;
import com.github.slick.sample.App;
import com.github.slick.sample.R;
import com.github.slick.Slick;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelegateDaggerFragment extends Fragment implements DelegateDaggerFragmentView {

    @Inject
    Provider<DelegateDaggerFragmentPresenter> provider;
    @Presenter
    DelegateDaggerFragmentPresenter presenter;


    public DelegateDaggerFragment() {
        // Required empty public constructor
    }

    public static DelegateDaggerFragment newInstance() {
        return new DelegateDaggerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_example, container, false);
        ((TextView) view.findViewById(R.id.text_view_fragment)).setText("Delegate Dagger Fragment.");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getDaggerComponent(getActivity()).inject(this);
        Slick.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Slick.onStart(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        Slick.onStop(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Slick.onDestroy(this);
        super.onDestroy();
        if (getActivity().isFinishing()) {
            App.disposeDaggerComponent(getActivity());
        }
    }
}
