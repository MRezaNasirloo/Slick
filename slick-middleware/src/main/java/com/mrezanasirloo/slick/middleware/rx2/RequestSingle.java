package com.mrezanasirloo.slick.middleware.rx2;


import com.mrezanasirloo.slick.middleware.BundleSlick;
import com.mrezanasirloo.slick.middleware.Middleware;
import com.mrezanasirloo.slick.middleware.Request;

import java.util.Arrays;
import java.util.Collections;

import io.reactivex.Single;
import io.reactivex.SingleObserver;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-13
 */

public abstract class RequestSingle<T extends Single<R>, R, P> extends Request<P> {
    private SingleObserver<R> source;

    public abstract T target(P data);

    public RequestSingle<T, R, P> with(P data) {
        this.data = data;
        if (!(data instanceof BundleSlick)) {
            bundleSlick = new BundleSlick().putParameter(data);
        }
        return this;
    }

    public RequestSingle<T, R, P> through(Middleware... middleware) {
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

    public void destination(SingleObserver<R> source) {
        this.source = source;
    }
}
