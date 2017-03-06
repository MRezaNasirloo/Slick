package test;

import android.os.Bundle;
import android.app.Activity;

import com.github.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class MultiInstanceDaggerActivity extends Activity implements ExampleView {

    @Inject
    Provider<ExamplePresenter> provider;
    @Presenter
    ExamplePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiInstanceDaggerActivity_Slick.bind(this);
        super.onCreate(savedInstanceState);
    }
}