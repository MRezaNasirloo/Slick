package com.github.pedramrn.samplemiddleware;

import com.github.slick.middleware.Middleware;
import com.github.slick.middleware.Request;
import com.github.slick.middleware.RequestData;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

public class SampleMiddleware implements Middleware {

    @Override
    public void handle(Request request, RequestData date) {
        request.next();
    }
}
