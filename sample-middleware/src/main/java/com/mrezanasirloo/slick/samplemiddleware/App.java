package com.mrezanasirloo.slick.samplemiddleware;

import android.app.Application;

import com.mrezanasirloo.slick.middleware.RequestStack;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-04-05
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RequestStack.getInstance().init(this);
    }
}
