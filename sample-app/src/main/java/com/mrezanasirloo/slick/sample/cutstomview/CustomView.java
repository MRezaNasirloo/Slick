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
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.SlickLifecycleListener;
import com.mrezanasirloo.slick.SlickUniqueId;

import java.util.UUID;

import static com.mrezanasirloo.slick.SlickDelegateActivity.SLICK_UNIQUE_KEY;
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
        ViewPresenter_Slick.bind(this);
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
        ViewPresenter_Slick.onDestroy(this);
    }

    @Nullable
    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putString(SLICK_UNIQUE_KEY, this.id);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.id = bundle.getString(SLICK_UNIQUE_KEY);
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    @NonNull
    @Override
    public String getUniqueId() {
        return id = (id != null ? id : UUID.randomUUID().toString());
    }
}
