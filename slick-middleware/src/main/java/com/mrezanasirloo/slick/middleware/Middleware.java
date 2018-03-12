package com.mrezanasirloo.slick.middleware;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-12
 */

public abstract class Middleware {
    abstract public void handle(Request request, BundleSlick bundle);
}
