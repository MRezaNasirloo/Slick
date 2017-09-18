package com.github.slick.sample.cutstomview.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.slick.sample.R;

public class DaggerCustomViewActivity extends AppCompatActivity {

    private DaggerCustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        customView = (DaggerCustomView) findViewById(R.id.custom_view_dagger);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customView.onDestroy();
    }
}
