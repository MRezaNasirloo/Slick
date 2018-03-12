package com.mrezanasirloo.slick.sample.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.sample.R;

public class FragmentHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_host);
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, SimpleSlickFragment.newInstance())
                    .commit();
        }
    }
}
