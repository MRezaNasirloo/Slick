package com.github.slick.middleware.rx2;


import com.github.slick.middleware.Middleware;
import com.github.slick.middleware.Request;
import com.github.slick.middleware.BundleSlick;

import org.reactivestreams.Subscriber;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.Flowable;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-13
 */

public abstract class RequestFlowable<T extends Flowable<R>, R, P> extends Request<P> {
    private Subscriber<R> source;

    public abstract T target(P data);

    public RequestFlowable<T, R, P> with(P data) {
        this.data = data;
        if (!(data instanceof BundleSlick)) {
            bundleSlick = new BundleSlick().putParameter(data);
        }
        return this;
    }

    public RequestFlowable<T, R, P> through(Middleware... middleware) {
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
        final T response = target(data);
        if (source != null) {
            response.subscribe(source);
        }
        tooLateAlreadyFinished = true;
    }

    public void destination(Subscriber<R> source) {
        this.source = source;
    }
}
