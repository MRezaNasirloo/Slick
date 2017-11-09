package test;

import com.github.slick.middleware.RequestSimple;
import com.github.slick.middleware.RequestStack;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import javax.inject.Inject;

public class RouterDaggerSlick extends RouterDagger {
    private final MiddlewareNoOp middlewareNoOp;

    @Inject
    public RouterDaggerSlick(MiddlewareNoOp middlewareNoOp) {
        super();
        this.middlewareNoOp = middlewareNoOp;
    }

    @Override
    public String method1(Integer id) {
        final RequestSimple<String, Integer> request = new RequestSimple<String, Integer>() {
            @Override
            public String target(Integer data) {
                return RouterDaggerSlick.super.method1(data);
            }
        };
        request.with(id).through(middlewareNoOp).destination(null);
        RequestStack.getInstance().push(request).processLastRequest();
        return null;
    }
}