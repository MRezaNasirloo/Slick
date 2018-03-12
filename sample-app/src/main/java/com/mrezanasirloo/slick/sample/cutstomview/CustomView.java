package com.mrezanasirloo.slick.sample.cutstomview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mrezanasirloo.slick.OnDestroyListener;
import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.R;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-09
 */

public class CustomView extends LinearLayout implements ExampleView, OnDestroyListener {

    @Presenter
    ViewPresenter presenter;

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
        ViewPresenter_Slick.bind(this);
        ViewPresenter_Slick.onAttach(this);

        final TextView textView = (TextView) findViewById(R.id.textView_custom_view);
        textView.setText(presenter.getData());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPresenter_Slick.onDetach(this);
    }

    @Override
    public void onDestroy() {
        ViewPresenter_Slick.onDestroy(this);
    }
}
