package test;

import com.github.slick.Middleware;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class RouterRx {

    @Middleware(MiddlewareNoOp.class)
    public Observable<String> method1(Integer id) {
        return Observable.just("Hello World");
    }

    @Middleware(MiddlewareNoOp2.class)
    public Observable<String> method2() {
        return Observable.just("Hello World");
    }

    @Middleware(MiddlewareNoOp2.class)
    public Flowable<String> method3() {
        return Flowable.just("Hello World");
    }

    @Middleware(MiddlewareNoOp2.class)
    public Maybe<String> method4() {
        return Maybe.just("Hello World");
    }

    @Middleware(MiddlewareNoOp2.class)
    public Single<String> method5(int id) {
        return Single.just("Hello World");
    }
}