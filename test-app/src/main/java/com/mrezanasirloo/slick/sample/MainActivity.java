package com.mrezanasirloo.slick.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrezanasirloo.slick.sample.activity.ActivitySimple;
import com.mrezanasirloo.slick.sample.activity.dagger.ActivitySimpleDagger;
import com.mrezanasirloo.slick.sample.conductor.ActivityConductorHost;
import com.mrezanasirloo.slick.sample.conductor.dagger.ActivityConductorHostDagger;
import com.mrezanasirloo.slick.sample.cutstomview.ActivityCustomView;
import com.mrezanasirloo.slick.sample.cutstomview.dagger.ActivityCustomViewDagger;
import com.mrezanasirloo.slick.sample.fragment.ActivityFragmentHost;
import com.mrezanasirloo.slick.sample.fragmentsupport.SupportFragmentHostActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivitySimple.class));
            }
        });
        findViewById(R.id.conductor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityConductorHost.class));
            }
        });
        findViewById(R.id.activity_dagger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivitySimpleDagger.class));
            }
        });
        findViewById(R.id.conductor_dagger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityConductorHostDagger.class));
            }
        });

        findViewById(R.id.fragment_delegate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityFragmentHost.class));
            }
        });

        findViewById(R.id.button_support_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SupportFragmentHostActivity.class));
            }
        });

        findViewById(R.id.button_custom_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityCustomView.class));
            }
        });

        findViewById(R.id.button_custom_view_dagger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityCustomViewDagger.class));
            }
        });
    }
}