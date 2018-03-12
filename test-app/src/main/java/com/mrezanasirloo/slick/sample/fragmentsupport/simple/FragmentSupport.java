package com.mrezanasirloo.slick.sample.fragmentsupport.simple;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSupport extends Fragment implements ViewFragmentSupport {

    @Presenter
    PresenterFragmentSupport presenter;

    public FragmentSupport() {
        // Required empty public constructor
    }

    public static FragmentSupport newInstance() {
        return new FragmentSupport();
    }

    @Nullable
    @Override
    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_example, container, false);
        ((TextView) view.findViewById(R.id.text_view_fragment))
                .setText("Delegate Fragment without base class");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PresenterFragmentSupport_Slick.bind(this, 1, "2");
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
