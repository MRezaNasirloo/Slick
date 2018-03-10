package com.github.slick.sample.cutstomview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.slick.OnDestroyListener;
import com.github.slick.Presenter;
import com.github.slick.sample.R;
import com.github.slick.sample.activity.ViewTestable;
import com.github.slick.test.SlickPresenterTestable;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-09
 */

public class CustomView extends LinearLayout implements ViewCustomView, OnDestroyListener {

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
        CustomView_Slick.bind(this);
        CustomView_Slick.onAttach(this);

        final TextView textView = (TextView) findViewById(R.id.textView_custom_view);
        textView.setText(presenter.getData());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CustomView_Slick.onDetach(this);
    }

    @Override
    public void onDestroy() {
        CustomView_Slick.onDestroy(this);
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}
