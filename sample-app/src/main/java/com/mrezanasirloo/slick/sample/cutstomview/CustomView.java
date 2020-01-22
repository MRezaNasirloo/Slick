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

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.SlickLifecycleListener;
import com.mrezanasirloo.slick.SlickUniqueId;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import static java.util.Locale.ENGLISH;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-09
 *
 *         A multi instance Custom View
 */

public class CustomView extends AppCompatTextView implements ViewCustomView, SlickLifecycleListener, SlickUniqueId {

    @Presenter
    ViewPresenter presenter;

    private String id;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) return;
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
        if (isInEditMode()) return;
        super.onDetachedFromWindow();
        ViewPresenter_Slick.onDetach(this);
    }

    @Override
    public void onBind(@NonNull String instanceId) {
        id = instanceId;
        ViewPresenter_Slick.bind(this);
    }

    @NonNull
    @Override
    public String getUniqueId() {
        return id;
    }
}
