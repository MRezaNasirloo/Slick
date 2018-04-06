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

package com.mrezanasirloo.slick.sample.multipresenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.R;

import java.util.Random;

/**
 * Demonstration of how to use multiple Presenters within one Activity, Note You can use the same logic with
 * Fragments, CustomViews and Conductor Controllers.
 * <p>
 * Using Separate Views for each Presenter is optional,
 * It is possible to use a shared view interface amongst multiple Presenters.
 */
public class ActivityMultiPresenter extends AppCompatActivity implements View1, View2 {

    @Presenter
    Presenter1 presenter1;

    @Presenter
    Presenter2 presenter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_presenter);
        Presenter1_Slick.bind(this, new Random().nextInt(), "John Doe");
        Presenter2_Slick.bind(this, "Foo Bar");
    }

    @Override
    public void setText1(String text) {
        ((TextView) findViewById(R.id.textView_multi_1)).setText(text);
    }

    @Override
    public void setText2(String text) {
        ((TextView) findViewById(R.id.textView_multi_2)).setText(text);
    }
}
