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

package com.mrezanasirloo.slick.sample.activity.dagger;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.App;
import com.mrezanasirloo.slick.sample.R;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySimpleDagger extends AppCompatActivity implements ViewSimpleDagger {

    @Inject
    Provider<PresenterSimpleDagger> provider;
    @Presenter
    PresenterSimpleDagger presenter;

    private static final String TAG = ActivitySimpleDagger.class.getSimpleName();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getDaggerComponent(this).inject(this);
        PresenterSimpleDagger_Slick.bind(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        TextView textView = findViewById(R.id.textView_simple);
        textView.setText("Activity's Presenter has injected with Dagger");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            Log.d(TAG, "onDestroy() called disposing");
            App.disposeDaggerComponent(this);
        }
    }
}
