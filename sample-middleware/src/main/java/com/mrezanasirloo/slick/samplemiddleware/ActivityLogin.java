package com.mrezanasirloo.slick.samplemiddleware;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mrezanasirloo.slick.middleware.RequestStack;

public class ActivityLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getDefaultSharedPreferences(ActivityLogin.this)
                        .edit().putBoolean("HAS_LOGGED_IN", true).apply();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        RequestStack.getInstance().handleBack();
        super.onBackPressed();
    }
}
