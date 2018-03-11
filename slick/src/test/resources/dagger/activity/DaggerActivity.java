package test;

import android.os.Bundle;
import android.app.Activity;

import com.github.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class DaggerActivity extends Activity implements ExampleView {

    @Inject
    Provider<ExamplePresenter> provider;
    @Presenter
    ExamplePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ExamplePresenter_Slick.bind(this);
        super.onCreate(savedInstanceState);
    }
}