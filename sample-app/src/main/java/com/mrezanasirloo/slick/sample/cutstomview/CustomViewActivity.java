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

package com.mrezanasirloo.slick.sample.cutstomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.sample.R;

public class CustomViewActivity extends AppCompatActivity {

    private CustomView customView1;
    private CustomView customView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        customView1 = findViewById(R.id.custom_view_1);
        customView2 = findViewById(R.id.custom_view_2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customView1.onDestroy(); //<--- onDestroy notification should be passed to view
        customView2.onDestroy();
    }
}
