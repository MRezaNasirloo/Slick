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

package com.mrezanasirloo.slick.samplemiddleware;

import android.os.Bundle;
import android.widget.ImageView;

import com.mrezanasirloo.slick.middleware.RequestStack;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityError extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        setTitle("No Network");
        ((ImageView) findViewById(R.id.imageView_error)).setImageDrawable(
                getResources().getDrawable(R.drawable.ic_signal_wifi_off_gray_24dp));
    }

    @Override
    public void onBackPressed() {
        RequestStack.getInstance().handleBack();
        super.onBackPressed();
    }
}