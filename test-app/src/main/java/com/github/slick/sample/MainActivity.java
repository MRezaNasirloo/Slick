package com.github.slick.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.slick.sample.activity.ActivitySimple;
import com.github.slick.sample.conductor.ConductorActivity;
import com.github.slick.sample.cutstomview.CustomViewActivity;
import com.github.slick.sample.cutstomview.dagger.DaggerCustomViewActivity;
import com.github.slick.sample.fragment.FragmentHostActivity;
import com.github.slick.sample.fragmentsupport.SupportFragmentHostActivity;


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
                startActivity(new Intent(MainActivity.this, ConductorActivity.class));
            }
        });
        findViewById(R.id.activity_dagger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, com.github.slick.sample.activity.dagger.ExampleActivity.class));
            }
        });
        findViewById(R.id.conductor_dagger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, com.github.slick.sample.conductor.dagger.ConductorActivity.class));
            }
        });

        findViewById(R.id.fragment_delegate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FragmentHostActivity.class));
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
                startActivity(new Intent(MainActivity.this, CustomViewActivity.class));
            }
        });

        findViewById(R.id.button_custom_view_dagger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DaggerCustomViewActivity.class));
            }
        });
    }
}
