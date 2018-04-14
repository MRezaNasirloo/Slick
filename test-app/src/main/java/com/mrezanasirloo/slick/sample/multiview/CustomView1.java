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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.SlickLifecycleListener;
import com.mrezanasirloo.slick.SlickUniqueId;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;
import com.mrezanasirloo.slick.sample.cutstomview.ViewCustomView;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import static java.util.Locale.ENGLISH;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 * Created on: 2017-03-09
 * <p>
 * A multi instance Custom View
 */

public class CustomView1 extends AppCompatTextView implements ViewCustomView, SlickLifecycleListener, SlickUniqueId {

    @Presenter
    ViewPresenter presenter;

    private String id;

    public CustomView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewPresenter_Slick.onAttach(this);

        String text = String.format(
                ENGLISH,
                "Presenter's code: %d, View's id: %s",
                presenter.getCode(),
                getUniqueId()
        );
        setText(text);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPresenter_Slick.onDetach(this);
    }

    @Override
    public void onBind(@NonNull String instanceId) {
        this.id = instanceId;
        ViewPresenter_Slick.bind(this);
    }

    @NonNull
    @Override
    public String getUniqueId() {
        return id;
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
