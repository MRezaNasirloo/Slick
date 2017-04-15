package com.github.pedramrn.samplemiddleware;

import org.junit.Test;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.operators.flowable.FlowablePublish;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.processors.ReplayProcessor;
import io.reactivex.subjects.CompletableSubject;
import io.reactivex.subjects.MaybeSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import io.reactivex.subjects.SingleSubject;
import io.reactivex.subjects.Subject;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
                final ReplaySubject<Integer> subject1 = ReplaySubject.create();
        MaybeSubject<Integer> subject = MaybeSubject.create();
        SingleSubject<Integer> subject3 = SingleSubject.create();
        CompletableSubject subject2 = CompletableSubject.create();

        final ReplayProcessor<Integer> replayProcessor = ReplayProcessor.create();

        Flowable.just(4).subscribe(replayProcessor);
        Completable.complete().subscribe(subject2);
        Single.just(3).subscribe(subject3);



Observable.just(2).subscribe(subject1);
        Maybe.just(1).subscribe(subject);
        subject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println("integer = [" + integer + "]");
            }
        });

        subject1.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println("integer = [" + integer + "]");
            }
        });

        subject2.subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                System.out.println("ExampleUnitTest.onComplete");
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        subject3.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println("integer = [" + integer + "]");
            }
        });

        replayProcessor.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println("integer = [" + integer + "]");
            }
        });

        /*subject.subscribe(new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("d = [" + d + "]");
            }

            @Override
            public void onSuccess(Integer integer) {
                System.out.println("integer = [" + integer + "]");
                System.out.println("ExampleUnitTest.onSuccess");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                System.out.println("ExampleUnitTest.onComplete");
            }
        });*/

        /*final Observable<Integer> observable = subject.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                System.out.println("disposable = [" + disposable + "]");
                subject.onNext(1);
                //or
                Observable.just(2, 3).subscribe(subject);
            }
        });

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("d = [" + d.isDisposed() + "]");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("item = [" + integer + "]");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });*//*
        expected:
        disposable = [false]
        d = [false]
        item = 1
        item = 2
        item = 3
        onComplete
        but received :
        disposable = [false]
        d = [false]
        onComplete
        */


        //        Observable.just(1,2).subscribe(subject);
        /*subject.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                System.out.println("item = " + integer);
            }
        });*/
        //        subject.subscribe(subject);//subscription
        //        subject.onNext(4);
        /*
        expected:
        item = 1
        item = 2
        item = 3
        but received :
        item = 3
        */
    }

}