/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.mrezanasirloo.slick.sample.multiview.ActivityMultiView;


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

        findViewById(R.id.button_multi_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityMultiView.class));
            }
        });

    }
}
