package com.mrezanasirloo.slick.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.R;

public class ExampleActivity extends AppCompatActivity implements ExampleActivityView {

    @Presenter
    ExampleActivityPresenter presenter;
    private static final String TAG = ExampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ExampleActivityPresenter_Slick.bind(this, R.id.textView3, "foo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
    }
}
