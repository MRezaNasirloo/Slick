package test;

import com.github.slick.Middleware;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


public class RouterGeneric<A, B extends String> {

    public RouterGeneric(String string, Integer integer, long lng) {
    }

    @Middleware({MiddlewareNoOp.class, MiddlewareNoOp2.class})
    public <R extends T, T> List<R> doSomething(B b) {
        return new ArrayList<>();
    }
}