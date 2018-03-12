package test;

import com.mrezanasirloo.slick.Middleware;

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