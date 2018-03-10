package com.github.slick.sample.activity.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.slick.Presenter;
import com.github.slick.sample.App;
import com.github.slick.sample.R;
import com.github.slick.Slick;
import com.github.slick.sample.activity.ViewTestable;
import com.github.slick.test.SlickPresenterTestable;

import javax.inject.Inject;
import javax.inject.Provider;

public class ActivitySimpleDagger extends AppCompatActivity implements ViewSimpleDagger {

    @Inject
    Provider<PresenterSimpleDagger> provider;
    @Presenter
    PresenterSimpleDagger presenter;

    private static final String TAG = ActivitySimpleDagger.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getDaggerComponent(this).inject(this);
        ActivitySimpleDagger_Slick.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            Log.d(TAG, "onDestroy() called disposing");
            App.disposeDaggerComponent(this);
        }
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
