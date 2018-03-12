package com.mrezanasirloo.slick.sample.cutstomview.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.sample.R;

public class DaggerCustomViewActivity extends AppCompatActivity {

    private DaggerCustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_dagger);
        customView = (DaggerCustomView) findViewById(R.id.custom_view_dagger);
    }

    @Override
    protected void onPause() {
        System.out.println("DaggerCustomViewActivity.onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("DaggerCustomViewActivity.onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("DaggerCustomViewActivity.onDestroy");
        super.onDestroy();
        customView.onDestroy();
    }
}
