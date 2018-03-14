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

package com.mrezanasirloo.slick.sample.cutstomview.dagger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.sample.R;

public class DaggerCustomViewActivity extends AppCompatActivity {

    private DaggerCustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_dagger);
        customView = (DaggerCustomView) findViewById(R.id.custom_view_dagger);
    }

    @Override
    protected void onPause() {
        System.out.println("DaggerCustomViewActivity.onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("DaggerCustomViewActivity.onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("DaggerCustomViewActivity.onDestroy");
        super.onDestroy();
        customView.onDestroy();
    }
}
