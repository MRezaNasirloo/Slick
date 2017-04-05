package com.github.slick.middleware.rx2;


import com.github.slick.middleware.Middleware;
import com.github.slick.middleware.Request;
import com.github.slick.middleware.SlickBundle;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.Completable;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-13
 */

public abstract class RequestCompletable<P> extends Request {
    private P data;
    private Completable source;

    public abstract Completable target(P data);

    public RequestCompletable<P> with(P data) {
        this.data = data;
        if (!(data instanceof SlickBundle)) {
            slickBundle = new SlickBundle().putParameter(data);
        }
        return this;
    }

    public RequestCompletable<P> through(Middleware... middleware) {
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
        final Completable response = target(data);
        if (source != null) {
            source.mergeWith(response);
        }
        tooLateAlreadyFinished = true;
    }

    public void destination(Completable source) {
        this.source = source;
    }
}
