package test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.slick.Presenter;
import com.github.slick.R;

public class ExampleActivity extends AppCompatActivity implements ExampleActivityView {

    @Presenter
    ExampleActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Slick.bind(this, R.id.textView3, "foo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
    }
}
