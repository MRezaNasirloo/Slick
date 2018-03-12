package com.mrezanasirloo.slick.samplemiddleware;

import android.content.SharedPreferences;

import com.mrezanasirloo.slick.middleware.BundleSlick;
import com.mrezanasirloo.slick.middleware.Middleware;
import com.mrezanasirloo.slick.middleware.Request;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-29
 */

public class MiddlewareLogin extends Middleware {


    private SharedPreferences sp;

    public MiddlewareLogin(SharedPreferences sp) {
        this.sp = sp;
    }

    @Override
    public void handle(Request request, BundleSlick date) {
        if (sp.getBoolean("HAS_LOGGED_IN", false)) {
            request.next();// process the next request
        } else {
            //            request.stopped();// optional, let the callback know about it
            Navigator.go(ActivityLogin.class);
        }
    }
}
