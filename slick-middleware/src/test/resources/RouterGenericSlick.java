package test;

import com.github.slick.middleware.RequestSimple;
import com.github.slick.middleware.RequestStack;

import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.List;

public class RouterGenericSlick<A, B extends String> extends RouterGeneric<A, B> {

    private final MiddlewareNoOp middlewareNoOp;
    private final MiddlewareNoOp2 middlewareNoOp2;

    public RouterGenericSlick(String string, Integer integer, long lng, MiddlewareNoOp middlewareNoOp,
                              MiddlewareNoOp2 middlewareNoOp2) {
        super(string, integer, lng);
        this.middlewareNoOp = middlewareNoOp;
        this.middlewareNoOp2 = middlewareNoOp2;
    }

    @Override
    public <R extends T, T> List<R> doSomething(B b) {
        final RequestSimple<List<R>, B> request = new RequestSimple<List<R>, B>() {
            @Override
            public List<R> target(B data) {
                return RouterGenericSlick.super.doSomething(data);
            }
        };
        request.with(b).through(middlewareNoOp, middlewareNoOp2).destination(null);
        RequestStack.getInstance().push(request).processLastRequest();
        return null;
    }
}