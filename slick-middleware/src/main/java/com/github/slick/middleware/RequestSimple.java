package com.github.slick.middleware;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-13
 */

public abstract class RequestSimple<R, P> extends Request {
    private P data;
    private Callback<R> callback;
    abstract public R target(P data);

    public RequestSimple<R, P> with(P data) {
        this.data = data;
        if (!(data instanceof SlickBundle)) {
            slickBundle = new SlickBundle().putParameter(data);
        }
        return this;
    }

    public RequestSimple<R, P> through(Middleware... middleware) {
        this.middleware = Arrays.asList(middleware);
        Collections.reverse(this.middleware);
        return this;
    }

    @Override
    public void next() {
        if (middlewareStack.size() > 0) {
            middlewareStack.pop().handle(this, slickBundle == null ? (SlickBundle) data : slickBundle);
        }
        middlewareBackStack++;
        if (!(middlewareBackStack - 1 == this.middleware.size() && !tooLateAlreadyFinished)) {
            return;
        }

        if (this != routerStack.pop()) throw new AssertionError();
        final R response = target(data);
        if (callback != null) {
            callback.onPass(response);
        }
        tooLateAlreadyFinished = true;
    }

    public void destination(Callback<R> callback) {
        this.callback = callback;
    }
}
