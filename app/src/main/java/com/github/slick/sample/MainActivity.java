package com.github.slick.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.slick.sample.activity.ExampleActivity;
import com.github.slick.sample.conductor.ConductorActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExampleActivity.class));
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
//                startActivity(new Intent(MainActivity.this, ConductorActivity.class));
                Toast.makeText(MainActivity.this, "TODO", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
