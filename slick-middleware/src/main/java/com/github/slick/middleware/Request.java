package com.github.slick.middleware;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-16
 */

public abstract class Request {
    public abstract void next();
    abstract void refill();
}
