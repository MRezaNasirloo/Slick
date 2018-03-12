package test;

import com.mrezanasirloo.slick.middleware.Middleware;
import com.mrezanasirloo.slick.middleware.Request;
import com.mrezanasirloo.slick.middleware.BundleSlick;

public class MiddlewareNoOp2 extends Middleware {

    @Override
    public void handle(Request request, BundleSlick date) {
        request.next();
    }
}