package test;

import android.os.Bundle;
import android.app.Activity;

import com.mrezanasirloo.slick.Presenter;

public class ExampleActivity extends Activity implements ExampleView {

    @Presenter
    ExamplePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ExamplePresenter_Slick.bind(this, 1, 2.0f);
        super.onCreate(savedInstanceState);
    }
}