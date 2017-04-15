package com.github.pedramrn.samplemiddleware;

import android.content.SharedPreferences;

import com.github.slick.middleware.Middleware;
import com.github.slick.middleware.Request;
import com.github.slick.middleware.BundleSlick;

/**
 * @author : Pedramrn@gmail.com
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
