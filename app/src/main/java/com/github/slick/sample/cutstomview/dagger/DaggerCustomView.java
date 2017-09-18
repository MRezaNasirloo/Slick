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

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-09
 */

public class DaggerCustomView extends LinearLayout implements ExampleView, OnDestroyListener {

    @Inject
    Provider<ViewPresenter> provider;
    @Presenter
    ViewPresenter presenter;

    public DaggerCustomView(Context context) {
        super(context);
    }

    public DaggerCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DaggerCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        App.getDaggerComponent(getContext()).inject(this);
        DaggerCustomView_Slick.bind(this);
        DaggerCustomView_Slick.onAttach(this);

        final TextView textView = (TextView) findViewById(R.id.textView_custom_view);
        textView.setText(presenter.getData());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        DaggerCustomView_Slick.onDetach(this);
    }

    @Override
    public void onDestroy() {
        DaggerCustomView_Slick.onDestroy(this);
    }
}
