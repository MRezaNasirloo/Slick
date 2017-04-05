package com.github.slick.middleware.rx2;


import com.github.slick.middleware.Middleware;
import com.github.slick.middleware.Request;
import com.github.slick.middleware.SlickBundle;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.Observable;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-13
 */

public abstract class RequestObservable<T extends Observable<R>, R, P> extends Request {
    private P data;
    private T source;

    public abstract T target(P data);

    public RequestObservable<T, R, P> with(P data) {
        this.data = data;
        if (!(data instanceof SlickBundle)) {
            slickBundle = new SlickBundle().putParameter(data);
        }
        return this;
    }

    public RequestObservable<T, R, P> through(Middleware... middleware) {
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
        final T response = target(data);
        if (source != null) {
            source.mergeWith(response);
        }
        tooLateAlreadyFinished = true;
    }

    public void destination(T source) {
        this.source = source;
    }
}
