package test;

import com.github.slick.Middleware;
import com.github.slick.middleware.Callback;

import javax.inject.Inject;


public class RouterDagger {

    @Inject
    public RouterDagger() {
    }

    @Middleware(MiddlewareNoOp.class)
    public String method1(Integer id) {
        return "Hello World";
    }
}