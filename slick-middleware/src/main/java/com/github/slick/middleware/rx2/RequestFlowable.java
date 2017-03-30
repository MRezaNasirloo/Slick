package com.github.slick.middleware.rx2;


import com.github.slick.middleware.Middleware;
import com.github.slick.middleware.Request;
import com.github.slick.middleware.RequestData;

import io.reactivex.Flowable;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-13
 */

public abstract class RequestFlowable<T extends Flowable<R>, R, P> extends Request {
    private P data;
    private T source;

    public abstract T target(P data);

    public RequestFlowable<T, R, P> with(P data) {
        this.data = data;
        if (!(data instanceof RequestData)) {
            requestData = new RequestData().putParameter(data);
        }
        return this;
    }

    public RequestFlowable<T, R, P> through(Middleware... middleware) {
        this.middleware = middleware;
        return this;
    }

    @Override
    public void next() {
        if (middlewareStack.size() > 0) {
            middlewareStack.pop().handle(this, requestData == null ? (RequestData) data : requestData);
        }
        middlewareBackStack++;
        if (!(middlewareBackStack - 1 == this.middleware.length && !tooLateAlreadyFinished)) {
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
