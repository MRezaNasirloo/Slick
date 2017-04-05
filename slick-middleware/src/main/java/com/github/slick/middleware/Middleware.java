package com.github.slick.middleware;


import android.content.Context;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-12
 */

public abstract class Middleware {
    abstract public void handle(Request request, SlickBundle bundle);

    /**
     * Override this method to initialize this middleware dependencies
     *
     * @param context application context
     */
    protected void init(Context context) {

    }
}
