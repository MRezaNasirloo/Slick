package com.github.pedramrn.samplemiddleware;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.github.slick.middleware.RequestStack;

public class ActivityError extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra("drawable_id")) {
            ((ImageView) findViewById(R.id.imageView_error)).setImageDrawable(
                    getResources().getDrawable(intent.getIntExtra("drawable_id", R.drawable.ic_signal_wifi_off_gray_24dp)));
        }
    }

    @Override
    public void onBackPressed() {
        RequestStack.getInstance().handleBack();
        super.onBackPressed();
    }
}