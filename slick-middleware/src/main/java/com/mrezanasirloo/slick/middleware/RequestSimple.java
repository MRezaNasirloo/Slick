package com.mrezanasirloo.slick.middleware;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-13
 */

public abstract class RequestSimple<R, P> extends Request<P> {
    private Callback<R> callback;

    abstract public R target(P data);

    public RequestSimple<R, P> with(P data) {
        this.data = data;
        if (!(data instanceof BundleSlick)) {
            bundleSlick = new BundleSlick().putParameter(data);
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
        if (!hasPassed()) {
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
