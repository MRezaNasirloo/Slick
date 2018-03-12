package com.mrezanasirloo.slick.samplemiddleware;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.IdRes;

import java.lang.ref.WeakReference;

/**
 * @author : Pedramrn@gmail.com
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
