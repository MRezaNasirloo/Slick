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

/**
 * A simple {@link Fragment} subclass.
 */
public class DelegateDaggerSlickFragment extends Fragment implements DelegateDaggerFragmentView {

    @Inject
    @Presenter
    DelegateDaggerFragmentPresenter presenter;


    public DelegateDaggerSlickFragment() {
        // Required empty public constructor
    }

    public static DelegateDaggerSlickFragment newInstance() {
        return new DelegateDaggerSlickFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        App.getDDaggerComponent(getActivity()).inject(this);
        Slick.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_example, container, false);
        ((TextView) view.findViewById(R.id.text_view_fragment)).setText("Delegate Dagger Fragment.");
        return view;
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
            App.disposeDDaggerComponent(getActivity());
        }
    }
}
