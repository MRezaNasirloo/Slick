package com.github.slick.sample.fragmentsupport.dagger;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.slick.Presenter;
import com.github.slick.Slick;
import com.github.slick.sample.App;
import com.github.slick.sample.R;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaggerFragmentSupport extends Fragment implements DaggerFragmentView {

    @Inject
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
    public void onAttach(Context context) {
        App.getDDaggerComponent(getActivity()).inject(this);
        Slick.bind(this);
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) {
            App.disposeDDaggerComponent(getActivity());
        }
    }
}
