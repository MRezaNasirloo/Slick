package com.github.slick.sample.activity.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.slick.Presenter;
import com.github.slick.sample.App;
import com.github.slick.sample.R;
import com.github.slick.sample.Slick;

import javax.inject.Inject;

public class ExampleActivity extends AppCompatActivity implements ExampleActivityView {

    @Inject
    @Presenter
    ExampleActivityPresenter presenter;

    private static final String TAG = ExampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getDDaggerComponent(this).inject(this);
        Slick.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        Log.e(TAG, presenter.toString());
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            Log.e(TAG, "onDestroy() called disposing");
            App.disposeDDaggerComponent(this);
        }
        super.onDestroy();
    }
}
