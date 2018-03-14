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

package com.mrezanasirloo.slick.samplemiddleware;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.IdRes;

import java.lang.ref.WeakReference;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-04-05
 */

public class Navigator {

    private static WeakReference<Activity> weakReference;

    public static void bind(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    public static void unbind() {
        weakReference.clear();
    }

    public static void go(Class<? extends Activity> activity) {
        final Activity context = weakReference.get();
        if (context != null) {
            context.startActivity(new Intent(context, activity));
        }
    }

    public static void go(@IdRes int id, Fragment fragment) {
        final Activity context = weakReference.get();
        if (context != null) {
            context.getFragmentManager().beginTransaction().replace(id, fragment).commit();
        }
    }
}
