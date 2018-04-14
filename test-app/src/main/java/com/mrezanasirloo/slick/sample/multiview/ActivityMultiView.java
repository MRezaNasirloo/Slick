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

package com.mrezanasirloo.slick.sample.multiview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.sample.R;

public class ActivityMultiView extends AppCompatActivity {

    CustomView1 customView1;
    CustomView1 customView1_2;
    CustomView2 customView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_view);
        customView1 = findViewById(R.id.custom_view_1);
        customView1_2 = findViewById(R.id.custom_view_1_2);
        customView2 = findViewById(R.id.custom_view_2);
        customView1.onBind(customView1.getUniqueId());
        customView1_2.onBind(customView1_2.getUniqueId());
        customView2.onBind(customView1_2.getUniqueId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewPresenter_Slick.onDestroy(customView1);
        ViewPresenter_Slick.onDestroy(customView1_2);
        ViewPresenter_Slick.onDestroy(customView2);
    }
}
