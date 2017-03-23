package test;

import android.database.Observable;
import android.support.annotation.Nullable;

import com.github.slick.Middleware;
import com.github.slick.middleware.RequestSimple;
import com.github.slick.middleware.RequestStack;

import java.util.ArrayList;
import java.util.List;

public class SimpleRouterSlick<A, B extends String> extends SimpleRouter<A, B> {

    private final MiddlewareNoOp middlewareNoOp;
    private final MiddlewareNoOp2 middlewareNoOp2;

    public SimpleRouterSlick(test.MiddlewareNoOp middlewareNoOp, MiddlewareNoOp2 middlewareNoOp2) {
        this.middlewareNoOp = middlewareNoOp;
        this.middlewareNoOp2 = middlewareNoOp2;
    }

    @Nullable
    @Override
    public <R extends T, T> List<R> doSomething(B b) {
        final RequestSimple<List<R>, R> request = new RequestSimple<List<R>, R>() {
            @Override
            public List<R> destination(R data) {
                return SimpleRouter.super.doSomthing(data);
            }
        };
        request.with(b).through(middlewareNoOp, middlewareNoOp2).destination(null);
        RequestStack.getInstance().push(request).processLastRequest();
        return null;
    }
}