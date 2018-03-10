package com.github.slick.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.slick.Presenter;
import com.github.slick.sample.R;
import com.github.slick.test.SlickPresenterTestable;

public class ActivitySimple extends AppCompatActivity implements ViewSimple {

    @Presenter
    PresenterSimple presenter;
    private static final String TAG = ActivitySimple.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivitySimple_Slick.bind(this, R.id.textView3, "foo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
