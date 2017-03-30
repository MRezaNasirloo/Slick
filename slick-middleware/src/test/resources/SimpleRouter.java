package test;

import com.github.slick.Middleware;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


public class SimpleRouter<A, B extends String> {

    public SimpleRouter(String string, Integer integer, long lng) {
    }

    @Middleware({MiddlewareNoOp.class, MiddlewareNoOp2.class})
    public <R extends T, T> List<R> doSomething(B b) {
        return new ArrayList<>();
    }

    @Middleware(MiddlewareNoOp.class)
    public Observable<String> doStuff(Integer integer) {
        return Observable.just(String.valueOf(integer));
    }
}