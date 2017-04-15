package test;

import com.github.slick.Middleware;
import com.github.slick.middleware.Callback;


public class RouterSimple {

    @Middleware(MiddlewareNoOp.class)
    public String method1(Integer id) {
        return "Hello World";
    }

    @Middleware(MiddlewareNoOp.class)
    public String method2(Integer id, Callback<String> callback) {
        return "Hello World";
    }

    @Middleware(MiddlewareNoOp.class)
    public String method3(Callback<String> callback) {
        return "Hello World";
    }

    @Middleware(MiddlewareNoOp.class)
    public String method4(Callback callback) {
        return "Hello World";
    }

    @Middleware(MiddlewareNoOp.class)
    public String method5() {
        return "Hello World";
    }

    @Middleware(MiddlewareNoOp.class)
    public void method6() {

    }
}