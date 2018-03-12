package com.mrezanasirloo.slick.sample.cutstomview.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.sample.R;

public class ActivityCustomViewDagger extends AppCompatActivity {

    CustomViewDagger customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_dagger);
        customView = (CustomViewDagger) findViewById(R.id.custom_view_dagger);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customView.onDestroy();
    }
}
