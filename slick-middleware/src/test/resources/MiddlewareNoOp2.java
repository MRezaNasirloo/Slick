package test;

import com.github.slick.middleware.Middleware;
import com.github.slick.middleware.Request;
import com.github.slick.middleware.RequestData;

public class MiddlewareNoOp2 implements Middleware {

    @Override
    public void handle(Request request, RequestData date) {
        request.next();
    }
}