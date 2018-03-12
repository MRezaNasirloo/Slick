package com.mrezanasirloo.slick.samplemiddleware;

import android.content.Context;
import android.net.ConnectivityManager;

import com.mrezanasirloo.slick.middleware.BundleSlick;
import com.mrezanasirloo.slick.middleware.Middleware;
import com.mrezanasirloo.slick.middleware.Request;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

public class MiddlewareInternetAccess extends Middleware {


    private final Context context;

    public MiddlewareInternetAccess(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public void handle(Request request, BundleSlick date) {
        if (isNetworkAvailable(context)) {
            request.next();// process the next request
        } else {
            //            request.stopped();// optional, let the callback know about it
            Navigator.go(ActivityError.class);
        }
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
