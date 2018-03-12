package com.mrezanasirloo.slick.middleware;


/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-12
 */

public abstract class Middleware {
    abstract public void handle(Request request, BundleSlick bundle);
}
