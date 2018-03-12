package test;

import com.mrezanasirloo.slick.Middleware;

import io.reactivex.Completable;

public class RouterCompletable {

    @Middleware(MiddlewareNoOp.class)
    public Completable method1(Integer id) {
        return Completable.complete();
    }

    @Middleware(MiddlewareNoOp.class)
    public Completable method2() {
        return Completable.complete();
    }
}