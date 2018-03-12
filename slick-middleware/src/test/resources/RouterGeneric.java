package test;

import com.mrezanasirloo.slick.Middleware;

import java.util.ArrayList;
import java.util.List;


public class RouterGeneric<A, B extends String> {

    public RouterGeneric(String string, Integer integer, long lng) {
    }

    @Middleware({MiddlewareNoOp.class, MiddlewareNoOp2.class})
    public <R extends T, T> List<R> doSomething(B b) {
        return new ArrayList<>();
    }
}