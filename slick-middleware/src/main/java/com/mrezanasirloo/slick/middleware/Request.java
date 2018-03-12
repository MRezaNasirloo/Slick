package com.mrezanasirloo.slick.middleware;

import java.util.List;
import java.util.Stack;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-16
 */

public abstract class Request<P> {
    protected RequestStack routerStack = RequestStack.getInstance();
    protected P data;
    protected List<Middleware> middleware;
    protected BundleSlick bundleSlick;
    protected Stack<Middleware> middlewareStack = new Stack<>();
    protected int middlewareBackStack = 0;
    protected boolean tooLateAlreadyFinished = false;

    public abstract void next();

    protected void refill() {
        middlewareStack.clear();
        middlewareStack.addAll(middleware);
        middlewareBackStack = 0;
    }

    protected boolean hasPassed() {
        if (middlewareStack.size() > 0) {
            middlewareStack.pop().handle(this, bundleSlick == null ? (BundleSlick) data : bundleSlick);
        }
        middlewareBackStack++;
        return (middlewareBackStack - 1 == this.middleware.size() && !tooLateAlreadyFinished);
    }
}
