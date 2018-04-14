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

public class ActivityCustomView extends AppCompatActivity {

    private CustomView customView1;
    private CustomView customView2;
    private String view2UniqueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        customView1 = findViewById(R.id.custom_view_1);
        customView2 = findViewById(R.id.custom_view_2);
        customView1.onBind("some_unique_id_1");
        customView2.onBind("some_unique_id_2");
        view2UniqueId = customView2.getUniqueId();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // onDestroy callbacks should be passed to the generated class
        ViewPresenter_Slick.onDestroy(customView1);
        ViewPresenter_Slick.onDestroy(customView2);
        // If you don't have access to the view anymore, i.e View's hosted in a fragment and the onDestroyView of the fragment has called
        // Retain its uniqueId and send the onDestroy callbacks yourself
        // ViewPresenter_Slick.onDestroy(view2UniqueId, this);
    }
}
