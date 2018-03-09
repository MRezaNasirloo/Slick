package com.github.slick.sample.cutstomview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.slick.SlickUniqueId;
import com.github.slick.sample.R;
import com.github.slick.sample.cutstomview.dagger.DaggerCustomView;

public class CustomViewActivity extends AppCompatActivity implements SlickUniqueId {

    private CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        customView = (CustomView) findViewById(R.id.custom_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customView.onDestroy();
    }

    @Override
    public String getUniqueId() {
        // TODO: 2017-09-18 do a multi instance test
        return null;
    }
}
