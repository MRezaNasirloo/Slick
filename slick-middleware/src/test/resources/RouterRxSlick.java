package test;

import com.mrezanasirloo.slick.middleware.RequestStack;
import com.mrezanasirloo.slick.middleware.rx2.RequestFlowable;
import com.mrezanasirloo.slick.middleware.rx2.RequestMaybe;
import com.mrezanasirloo.slick.middleware.rx2.RequestObservable;
import com.mrezanasirloo.slick.middleware.rx2.RequestSingle;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.processors.ReplayProcessor;
import io.reactivex.subjects.MaybeSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.SingleSubject;

import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.Void;

public class RouterRxSlick extends RouterRx {
    private final MiddlewareNoOp middlewareNoOp;

    private final MiddlewareNoOp2 middlewareNoOp2;

    public RouterRxSlick(MiddlewareNoOp middlewareNoOp, MiddlewareNoOp2 middlewareNoOp2) {
        super();
        this.middlewareNoOp = middlewareNoOp;
        this.middlewareNoOp2 = middlewareNoOp2;
    }

    @Override
    public Observable<String> method1(Integer id) {
        final RequestObservable<Observable<String>, String, Integer> request =
                new RequestObservable<Observable<String>, String, Integer>() {
                    @Override
                    public Observable<String> target(Integer data) {
                        return RouterRxSlick.super.method1(data);
                    }
                };
        final ReplaySubject<String> source = ReplaySubject.create();
        request.with(id).through(middlewareNoOp).destination(source);
        RequestStack.getInstance().push(request).processLastRequest();
        return source;
    }

    @Override
    public Observable<String> method2() {
        final RequestObservable<Observable<String>, String, Void> request =
                new RequestObservable<Observable<String>, String, Void>() {
                    @Override
                    public Observable<String> target(Void data) {
                        return RouterRxSlick.super.method2();
                    }
                };
        final ReplaySubject<String> source = ReplaySubject.create();
        request.with(null).through(middlewareNoOp2).destination(source);
        RequestStack.getInstance().push(request).processLastRequest();
        return source;
    }

    @Override
    public Flowable<String> method3() {
        final RequestFlowable<Flowable<String>, String, Void> request =
                new RequestFlowable<Flowable<String>, String, Void>() {
                    @Override
                    public Flowable<String> target(Void data) {
                        return RouterRxSlick.super.method3();
                    }
                };
        final ReplayProcessor<String> source = ReplayProcessor.create();
        request.with(null).through(middlewareNoOp2).destination(source);
        RequestStack.getInstance().push(request).processLastRequest();
        return source;
    }

    @Override
    public Maybe<String> method4() {
        final RequestMaybe<Maybe<String>, String, Void> request = new RequestMaybe<Maybe<String>, String, Void>() {
            @Override
            public Maybe<String> target(Void data) {
                return RouterRxSlick.super.method4();
            }
        };
        final MaybeSubject<String> source = MaybeSubject.create();
        request.with(null).through(middlewareNoOp2).destination(source);
        RequestStack.getInstance().push(request).processLastRequest();
        return source;
    }

    @Override
    public Single<String> method5(int id) {
        final RequestSingle<Single<String>, String, Integer> request =
                new RequestSingle<Single<String>, String, Integer>() {
                    @Override
                    public Single<String> target(Integer data) {
                        return RouterRxSlick.super.method5(data);
                    }
                };
        final SingleSubject<String> source = SingleSubject.create();
        request.with(id).through(middlewareNoOp2).destination(source);
        RequestStack.getInstance().push(request).processLastRequest();
        return source;
    }
}