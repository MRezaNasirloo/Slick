package com.github.slick.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.slick.Presenter;
import com.github.slick.sample.R;
import com.github.slick.Slick;

public class ExampleActivity extends AppCompatActivity implements ExampleActivityView {

    @Presenter
    ExampleActivityPresenter presenter;
    private static final String TAG = ExampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //        ExampleActivity_Slick.bind();
        Slick.bind(this, R.id.textView3, "foo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        //        Log.e(TAG, presenter.toString());
    }

    public static String foo(ExampleActivity exampleActivity, Integer i, String s) {
        return "boo yah";
    }
}
