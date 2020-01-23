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

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.SlickLifecycleListener;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-09
 */

public class CustomView extends LinearLayout implements ViewCustomView, SlickLifecycleListener {

    @Presenter
    PresenterCustomView presenter;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        PresenterCustomView_Slick.onAttach(this);
        final TextView textView = findViewById(R.id.textView_custom_view);
        textView.setText(presenter.getData());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PresenterCustomView_Slick.onDetach(this);
    }

    @Override
    public void onBind(@NonNull String instanceId) {
        PresenterCustomView_Slick.bind(this);
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
