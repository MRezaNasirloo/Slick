package com.github.slick.sample.fragmentsupport.simple;


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
import com.github.slick.sample.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment implements SupportFragmentView {

    @Presenter
    SupportFragmentPresenter presenter;

    public SupportFragment() {
        // Required empty public constructor
    }

    public static SupportFragment newInstance() {
        return new SupportFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_example, container, false);
        ((TextView) view.findViewById(R.id.text_view_fragment))
                .setText("Delegate Fragment without base class");
        return view;
    }

    @Override
    public void onAttach(Context context) {
        Slick.bind(this, 1, "2");
        super.onAttach(context);
    }
}
