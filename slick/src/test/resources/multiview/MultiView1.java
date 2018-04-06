package test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mrezanasirloo.slick.OnDestroyListener;
import com.mrezanasirloo.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class MultiView1 extends LinearLayout implements ExampleView, OnDestroyListener {

    @Inject
    Provider<ExamplePresenter> provider;

    @Presenter
    ExamplePresenter presenter;

    public MultiView1(Context context) {
        super(context);
    }

    public MultiView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ExamplePresenter_Slick.bind(this);
        ExamplePresenter_Slick.onAttach(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ExamplePresenter_Slick.onDetach(this);
    }

    @Override
    public void onDestroy() {
        ExamplePresenter_Slick.onDestroy(this);
    }
}