package test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.slick.OnDestroyListener;
import com.github.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class DaggerCustomView extends LinearLayout implements ExampleView, OnDestroyListener {

    @Inject
    Provider<ExamplePresenter> provider;

    @Presenter
    ExamplePresenter presenter;

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
        DaggerCustomView_Slick.bind(this);
        DaggerCustomView_Slick.onAttach(this);
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