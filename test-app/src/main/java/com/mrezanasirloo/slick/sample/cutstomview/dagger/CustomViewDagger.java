package com.mrezanasirloo.slick.sample.cutstomview.dagger;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrezanasirloo.slick.OnDestroyListener;
import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.App;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.sample.activity.ViewTestable;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-09
 */

public class CustomViewDagger extends LinearLayout implements ViewCustomViewDagger, OnDestroyListener {

    @Inject
    Provider<PresenterCustomViewDagger> provider;
    @Presenter
    PresenterCustomViewDagger presenter;

    public CustomViewDagger(Context context) {
        super(context);
    }

    public CustomViewDagger(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewDagger(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        System.out.println("DaggerCustomView.onAttachedToWindow");
        super.onAttachedToWindow();
        App.getDaggerComponent(getContext()).inject(this);
        PresenterCustomViewDagger_Slick.bind(this);
        PresenterCustomViewDagger_Slick.onAttach(this);

        final TextView textView = (TextView) findViewById(R.id.textView_custom_view);
        textView.setText(presenter.getData());
    }

    @Override
    protected void onDetachedFromWindow() {
        System.out.println("DaggerCustomView.onDetachedFromWindow");
        super.onDetachedFromWindow();
        PresenterCustomViewDagger_Slick.onDetach(this);
    }

    @Override
    public void onDestroy() {
        System.out.println("DaggerCustomView.onDestroy");
        PresenterCustomViewDagger_Slick.onDestroy(this);
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
