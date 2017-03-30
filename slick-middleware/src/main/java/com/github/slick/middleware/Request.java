package com.github.slick.middleware;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-16
 */

public abstract class Request {
    protected RequestStack routerStack = RequestStack.getInstance();
    protected Middleware[] middleware;
    protected RequestData requestData;
    protected Stack<Middleware> middlewareStack = new Stack<>();
    protected int middlewareBackStack = 0;
    protected boolean tooLateAlreadyFinished = false;

    public abstract void next();

    protected void refill() {
        middlewareStack.clear();
        middlewareStack.addAll(Arrays.asList(middleware));
    }

}
