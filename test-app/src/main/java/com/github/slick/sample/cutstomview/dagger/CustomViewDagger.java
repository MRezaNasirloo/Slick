package com.github.slick.sample.cutstomview.dagger;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.slick.OnDestroyListener;
import com.github.slick.Presenter;
import com.github.slick.sample.App;
import com.github.slick.sample.R;
import com.github.slick.sample.activity.ViewTestable;
import com.github.slick.test.SlickPresenterTestable;

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
        CustomViewDagger_Slick.bind(this);
        CustomViewDagger_Slick.onAttach(this);

        final TextView textView = (TextView) findViewById(R.id.textView_custom_view);
        textView.setText(presenter.getData());
    }

    @Override
    protected void onDetachedFromWindow() {
        System.out.println("DaggerCustomView.onDetachedFromWindow");
        super.onDetachedFromWindow();
        CustomViewDagger_Slick.onDetach(this);
    }

    @Override
    public void onDestroy() {
        System.out.println("DaggerCustomView.onDestroy");
        CustomViewDagger_Slick.onDestroy(this);
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
