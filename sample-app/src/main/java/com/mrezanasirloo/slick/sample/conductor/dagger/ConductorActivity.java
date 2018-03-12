package com.mrezanasirloo.slick.sample.conductor.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.mrezanasirloo.slick.sample.R;

public class ConductorActivity extends AppCompatActivity implements ConductorView {

    private Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductor);

        ViewGroup container = (ViewGroup) findViewById(R.id.con_controller_container);

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new ExampleController()));
        }
    }
}
