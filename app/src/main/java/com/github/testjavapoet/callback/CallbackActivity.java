package com.github.testjavapoet.callback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.testjavapoet.R;

public class CallbackActivity extends AppCompatActivity implements CallBackView {

    private CallBackPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = CallBackPresenter_HOST.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
    }
}
