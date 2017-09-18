package test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.slick.OnDestroyListener;
import com.github.slick.Presenter;

public class ExampleCustomView extends LinearLayout implements ExampleView, OnDestroyListener {

    @Presenter
    ExamplePresenter presenter;

    public ExampleCustomView(Context context) {
        super(context);
    }

    public ExampleCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExampleCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ExampleCustomView_Slick.bind(this, 1, 2f);
        ExampleCustomView_Slick.onAttach(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ExampleCustomView_Slick.onDetach(this);
    }

    @Override
    public void onDestroy() {
        ExampleCustomView_Slick.onDestroy(this);
    }
}