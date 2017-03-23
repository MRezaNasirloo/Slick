package com.github.slick.middleware;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-12
 */

public interface Middleware {
    void handle(Request request, RequestData date);
}
