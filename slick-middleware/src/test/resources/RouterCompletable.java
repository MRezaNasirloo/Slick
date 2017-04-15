package test;

import com.github.slick.Middleware;
import com.github.slick.middleware.Callback;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

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