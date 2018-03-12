package com.mrezanasirloo.slick.sample.fragmentsupport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.sample.R;

public class SupportFragmentHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_host);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, HostFragment.newInstance())
                    .commit();
        }
    }
}
