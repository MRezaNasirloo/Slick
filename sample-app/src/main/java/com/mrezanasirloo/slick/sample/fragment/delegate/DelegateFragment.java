package com.mrezanasirloo.slick.sample.fragment.delegate;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.Slick;
import com.mrezanasirloo.slick.sample.R;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Slick.bind(this, 1, "2");
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
    }

}
