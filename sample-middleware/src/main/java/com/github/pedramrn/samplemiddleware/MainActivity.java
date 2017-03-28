package com.github.pedramrn.samplemiddleware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.slick.middleware.Callback;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SampleRouter router = new SampleRouterSlick(new SampleMiddleware());
        router.voteUp("1", new Callback<Boolean>() {
            @Override
            public void onPass(Boolean aBoolean) {

            }
        });


    }
}
