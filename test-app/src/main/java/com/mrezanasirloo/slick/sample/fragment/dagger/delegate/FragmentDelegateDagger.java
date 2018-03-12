package com.mrezanasirloo.slick.sample.fragment.dagger.delegate;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.App;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDelegateDagger extends Fragment implements ViewFragmentDelegateDagger {

    @Inject
    Provider<PresenterFragmentDelegateDagger> provider;
    @Presenter
    PresenterFragmentDelegateDagger presenter;


    public FragmentDelegateDagger() {
        // Required empty public constructor
    }

    public static FragmentDelegateDagger newInstance() {
        return new FragmentDelegateDagger();
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
        PresenterFragmentDelegateDagger_Slick.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        PresenterFragmentDelegateDagger_Slick.onStart(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        PresenterFragmentDelegateDagger_Slick.onStop(this);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PresenterFragmentDelegateDagger_Slick.onDestroy(this);
        if (getActivity().isFinishing()) {
            App.disposeDaggerComponent(getActivity());
        }
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
