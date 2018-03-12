package com.mrezanasirloo.slick.sample.cutstomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.SlickUniqueId;
import com.mrezanasirloo.slick.sample.R;

public class ActivityCustomView extends AppCompatActivity implements SlickUniqueId {

    CustomView customView;

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
