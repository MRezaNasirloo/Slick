package com.github.slick.sample.fragment.delegate;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.slick.Presenter;
import com.github.slick.sample.R;
import com.github.slick.sample.activity.ViewTestable;
import com.github.slick.test.SlickPresenterTestable;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDelegate extends Fragment implements ViewFragmentDelegate {

    @Presenter
    PresenterFragmentDelegate presenter;

    public FragmentDelegate() {
        // Required empty public constructor
    }

    public static FragmentDelegate newInstance() {
        return new FragmentDelegate();
    }

    @Nullable
    @Override
    @SuppressLint("SetTextI18n")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_example, container, false);
        ((TextView) view.findViewById(R.id.text_view_fragment)).setText("Delegate Fragment without base class");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        FragmentDelegate_Slick.bind(this, 1, "2");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        FragmentDelegate_Slick.onStart(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        FragmentDelegate_Slick.onStop(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        FragmentDelegate_Slick.onDestroy(this);
        super.onDestroy();
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
