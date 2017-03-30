package test;

import com.github.slick.middleware.RequestSimple;
import com.github.slick.middleware.RequestStack;
import com.github.slick.middleware.rx2.RequestObservable;

import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SimpleRouterSlick<A, B extends String> extends SimpleRouter<A, B> {

    private final MiddlewareNoOp middlewareNoOp;
    private final MiddlewareNoOp2 middlewareNoOp2;

    public SimpleRouterSlick(String string, Integer integer, long lng, MiddlewareNoOp middlewareNoOp, MiddlewareNoOp2 middlewareNoOp2) {
        super(string, integer, lng);
        this.middlewareNoOp = middlewareNoOp;
        this.middlewareNoOp2 = middlewareNoOp2;
    }

    @Override
    public <R extends T, T> List<R> doSomething(B b) {
        final RequestSimple<List<R>, B> request = new RequestSimple<List<R>, B>() {
            @Override
            public List<R> target(B data) {
                return SimpleRouterSlick.super.doSomething(data);
            }
        };
        request.with(b).through(middlewareNoOp, middlewareNoOp2).destination(null);
        RequestStack.getInstance().push(request).processLastRequest();
        return null;
    }

    @Override
    public Observable<String> doStuff(Integer integer) {
        final RequestObservable<Observable<String>, String, Integer> request =
                new RequestObservable<Observable<String>, String, Integer>() {
            @Override
            public Observable target(Integer data) {
                return SimpleRouterSlick.super.doStuff(data);
            }
        };
        final PublishSubject<String> source = PublishSubject.create();
        request.with(integer).through(middlewareNoOp).destination(source);
        RequestStack.getInstance().push(request).processLastRequest();
        return source;
    }
}