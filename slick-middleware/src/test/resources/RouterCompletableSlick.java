package test;

import com.github.slick.middleware.RequestStack;
import com.github.slick.middleware.rx2.RequestCompletable;

import io.reactivex.Completable;
import io.reactivex.subjects.CompletableSubject;

import java.lang.Integer;
import java.lang.Override;
import java.lang.Void;

public class RouterCompletableSlick extends RouterCompletable {

    private final MiddlewareNoOp middlewareNoOp;

    public RouterSimpleSlick(MiddlewareNoOp middlewareNoOp) {
        super();
        this.middlewareNoOp = middlewareNoOp;
    }

    @Override
    public Completable method1(Integer id) {
        final RequestCompletable<Integer> request = new RequestCompletable<Integer>() {
            @Override
            public Completable target(Integer data) {
                return RouterCompletableSlick.super.method1(data);
            }
        };
        final CompletableSubject source = CompletableSubject.create();
        request.with(id).through(middlewareNoOp).destination(source);
        RequestStack.getInstance().push(request).processLastRequest();
        return source;

    }

    @Override
    public Completable method2() {
        final RequestCompletable<Void> request = new RequestCompletable<Void>() {
            @Override
            public Completable target(Void data) {
                return RouterCompletableSlick.super.method2();
            }
        };
        final CompletableSubject source = CompletableSubject.create();
        request.with(null).through(middlewareNoOp).destination(source);
        RequestStack.getInstance().push(request).processLastRequest();
        return source;
    }
}