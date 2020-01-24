package test;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mrezanasirloo.slick.SlickLifecycleListener;
import com.mrezanasirloo.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class DaggerCustomView extends LinearLayout implements ExampleView, SlickLifecycleListener {

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
        ExamplePresenter_Slick.onAttach(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ExamplePresenter_Slick.onDetach(this);
    }

    @Override
    public void onBind(@NonNull String instanceId) {
        ExamplePresenter_Slick.bind(this);
    }
}