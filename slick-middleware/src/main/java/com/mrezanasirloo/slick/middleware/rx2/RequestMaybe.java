package com.mrezanasirloo.slick.middleware.rx2;


import com.mrezanasirloo.slick.middleware.BundleSlick;
import com.mrezanasirloo.slick.middleware.Middleware;
import com.mrezanasirloo.slick.middleware.Request;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-13
 */

public abstract class RequestMaybe<T extends Maybe<R>, R, P> extends Request<P> {
    private MaybeObserver<R> source;

    public abstract T target(P data);

    public RequestMaybe<T, R, P> with(P data) {
        this.data = data;
        if (!(data instanceof BundleSlick)) {
            bundleSlick = new BundleSlick().putParameter(data);
        }
        return this;
    }

    public RequestMaybe<T, R, P> through(Middleware... middleware) {
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

    public void destination(MaybeObserver<R> source) {
        this.source = source;
    }
}
