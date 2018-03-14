/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick.sample;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.mrezanasirloo.slick.sample.activity.dagger.DaggerModule;
import com.mrezanasirloo.slick.sample.di.AppComponent;
import com.mrezanasirloo.slick.sample.di.AppModule;
import com.mrezanasirloo.slick.sample.di.DaggerAppComponent;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-01
 */

public class App extends Application {
    private static final String TAG = App.class.getSimpleName();

    private AppComponent component;
    private AppComponent.DaggerComponent dDaggerComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    public static AppComponent.DaggerComponent getDaggerComponent(Context context) {
        final App app = (App) context.getApplicationContext();
        if (app.dDaggerComponent == null) {
            app.dDaggerComponent = app.component.add(new DaggerModule());
        }
        return app.dDaggerComponent;
    }

    public static void disposeDaggerComponent(Context context) {
        ((App) context.getApplicationContext()).dDaggerComponent = null;
        Log.d(TAG, "disposeDDaggerComponent() called");
    }
}
