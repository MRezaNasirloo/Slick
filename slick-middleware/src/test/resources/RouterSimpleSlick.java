package test;

import com.github.slick.middleware.Callback;
import com.github.slick.middleware.RequestSimple;
import com.github.slick.middleware.RequestStack;

import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.Void;

public class RouterSimpleSlick extends RouterSimple {
    private final MiddlewareNoOp middlewareNoOp;

    public RouterSimpleSlick(MiddlewareNoOp middlewareNoOp) {
        super();
        this.middlewareNoOp = middlewareNoOp;
    }

    @Override
    public String method1(Integer id) {
        final RequestSimple<String, Integer> request = new RequestSimple<String, Integer>() {
            @Override
            public String target(Integer data) {
                return RouterSimpleSlick.super.method1(data);
            }
        };
        request.with(id).through(middlewareNoOp).destination(null);
        RequestStack.getInstance().push(request).processLastRequest();
        return null;
    }

    @Override
    public String method2(Integer id, Callback<String> callback) {
        final RequestSimple<String, Integer> request = new RequestSimple<String, Integer>() {
            @Override
            public String target(Integer data) {
                return RouterSimpleSlick.super.method2(data, null);
            }
        };
        request.with(id).through(middlewareNoOp).destination(callback);
        RequestStack.getInstance().push(request).processLastRequest();
        return null;
    }

    @Override
    public String method3(Callback<String> callback) {
        final RequestSimple<String, Void> request = new RequestSimple<String, Void>() {
            @Override
            public String target(Void data) {
                return RouterSimpleSlick.super.method3(null);
            }
        };
        request.with(null).through(middlewareNoOp).destination(callback);
        RequestStack.getInstance().push(request).processLastRequest();
        return null;
    }

    @Override
    public String method4(Callback callback) {
        final RequestSimple<String, Void> request = new RequestSimple<String, Void>() {
            @Override
            public String target(Void data) {
                return RouterSimpleSlick.super.method4(null);
            }
        };
        request.with(null).through(middlewareNoOp).destination(callback);
        RequestStack.getInstance().push(request).processLastRequest();
        return null;
    }

    @Override
    public String method5() {
        final RequestSimple<String, Void> request = new RequestSimple<String, Void>() {
            @Override
            public String target(Void data) {
                return RouterSimpleSlick.super.method5();
            }
        };
        request.with(null).through(middlewareNoOp).destination(null);
        RequestStack.getInstance().push(request).processLastRequest();
        return null;
    }

    @Override
    public void method6() {
        final RequestSimple<Void, Void> request = new RequestSimple<Void, Void>() {
            @Override
            public Void target(Void data) {
                RouterSimpleSlick.super.method6();
                return null;
            }
        };
        request.with(null).through(middlewareNoOp).destination(null);
        RequestStack.getInstance().push(request).processLastRequest();
    }
}