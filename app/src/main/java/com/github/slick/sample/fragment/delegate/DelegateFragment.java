package com.github.slick.sample.fragment.delegate;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.slick.Presenter;
import com.github.slick.sample.R;
import com.github.slick.Slick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DelegateFragment extends Fragment implements DelegateFragmentView {

    @Presenter
    DelegateFragmentPresenter presenter;

    public DelegateFragment() {
        // Required empty public constructor
    }

    public static DelegateFragment newInstance() {
        return new DelegateFragment();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slick.bind(this, 1, "2");
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
    }

}
