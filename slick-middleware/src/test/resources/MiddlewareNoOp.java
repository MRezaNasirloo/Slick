package test;

import com.github.slick.middleware.Middleware;
import com.github.slick.middleware.Request;
import com.github.slick.middleware.SlickBundle;

public class MiddlewareNoOp implements Middleware {

    @Override
    public void handle(Request request, SlickBundle date) {
        request.next();
    }
}