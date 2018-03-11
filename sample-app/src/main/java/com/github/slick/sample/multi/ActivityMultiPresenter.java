package com.github.slick.sample.multi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.slick.Presenter;
import com.github.slick.sample.R;

import java.util.Random;

/**
 * Demonstration of how to use multiple Presenters within one Activity, Note You can use the same logic with
 * Fragments, CustomViews and Conductor Controllers.
 * <p>
 * Using Separate Views for each Presenter is optional,
 * It is possible to use a shared view interface amongst multiple Presenters.
 */
public class ActivityMultiPresenter extends AppCompatActivity implements View1, View2 {

    @Presenter
    Presenter1 presenter1;

    @Presenter
    Presenter2 presenter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_presenter);
        Presenter1_Slick.bind(this, new Random().nextInt(), "John Doe");
        Presenter2_Slick.bind(this, "Foo Bar");
    }

    @Override
    public void setText1(String text) {
        ((TextView) findViewById(R.id.textView_multi_1)).setText(text);
    }

    @Override
    public void setText2(String text) {
        ((TextView) findViewById(R.id.textView_multi_2)).setText(text);
    }
}
