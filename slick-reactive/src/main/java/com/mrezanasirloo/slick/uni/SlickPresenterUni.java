/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick.uni;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.mrezanasirloo.slick.SlickPresenter;

import java.util.Arrays;

import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-07-22
 *
 *         Inspired from Elm archtechture and articles about unidrirectinal data flow around the web.
 *
 *         This is an abstract subclass of {@link SlickPresenter} which practice the idea of
 *         Uni-Directional data flow, It's based on reactive streams and uses RxJava2's streams vastly.
 *
 *         How to use: Subclass it and implement the abstact methods, use the {@link SlickPresenterUni#start(Object)}
 *         to build the data stream object. the View is provided for you, it can be used to create {@link SlickPresenterUni#command(CommandProvider)}
 *         which reacts to view and sends a new {@link PartialViewState} to the {@link SlickPresenterUni#render(Object, Object)} method.
 */

public abstract class SlickPresenterUni<V, S> extends SlickPresenter<V> implements Observer<S> {
    private static final String TAG = SlickPresenterUni.class.getSimpleName();
    protected final Scheduler io;
    protected final Scheduler main;
    @NonNull
    private final BehaviorSubject<S> state = BehaviorSubject.create();
    @NonNull
    private final CompositeDisposable disposable = new CompositeDisposable();
    private CompositeDisposable disposableCommands;
    @NonNull
    private final ArrayMap<CommandProvider, PublishSubject> commands = new ArrayMap<>(3);
    private boolean hasSubscribed;

    public SlickPresenterUni(@Named("main") Scheduler main, @Named("io") Scheduler io) {
        this.main = main;
        this.io = io;
    }

    @CallSuper
    @Override
    public void onViewUp(@NonNull V view) {
        super.onViewUp(view);
        if (!hasSubscribed()) {
            start(view);
        }
        subscribeIntents(view);
        disposableCommands.add(updateStream().subscribe(new Consumer<S>() {
            @Override
            public void accept(@NonNull S state) {
                if (getView() != null) render(state, getView());
            }
        }));
    }

    @CallSuper
    @Override
    public void onViewDown() {
        super.onViewDown();
        dispose(disposableCommands);
    }

    @CallSuper
    @Override
    public void onDestroy() {
        dispose(disposable);
        commands.clear();
        super.onDestroy();
    }

    /**
     * This method is called only once during the presenter's lifecycle
     *
     * @param view the view which is bounded to this presenter
     */
    protected abstract void start(@NonNull V view);

    @NonNull
    public Observable<S> updateStream() {
        return state;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        hasSubscribed = true;
        disposable.add(d);
    }

    @Override
    public void onNext(@NonNull S newState) {
        state.onNext(newState);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        //fail early
        System.out.println(TAG + " onError: Called");
        throw new RuntimeException(e);
    }

    @Override
    public void onComplete() {
        System.out.println(TAG + " onComplete() called");
    }

    /**
     * @param initialState the initial state to render to view
     * @param partialViewState the stream of partial view states
     * @return A stream of ViewState
     */
    @NonNull
    protected Observable<S> reduce(@NonNull S initialState, @NonNull Observable<PartialViewState<S>> partialViewState) {
        return partialViewState.observeOn(main)
                .scan(initialState, new BiFunction<S, PartialViewState<S>, S>() {
                    @Override
                    public S apply(S oldState, @NonNull PartialViewState<S> partialViewState1) throws Exception {
                        return partialViewState1.reduce(oldState);
                    }
                });
    }

    /**
     * It's a Synonym for calling:
     * <br>
     *      <code>reduce(initialState, streams).subscribe(this);</code>
     *
     * @param initialState the initial state to render to view
     * @param partialViewState the stream of partial view states
     */
    protected void subscribe(@NonNull S initialState, @NonNull Observable<PartialViewState<S>> partialViewState) {
        reduce(initialState, partialViewState).subscribe(this);
    }


    /**
     * Utility method to merge all command's results to a single stream
     *
     * @param partials command's results
     * @return stream of partials
     */
    @NonNull
    @SafeVarargs
    protected final Observable<PartialViewState<S>> merge(@NonNull Observable<PartialViewState<S>>... partials) {
        return Observable.merge(Arrays.asList(partials));
    }

    /**
     * Creates command from view streams
     *
     * @param cmd command provider from view
     * @param <T> return type of command
     * @return a command to be executed
     */
    @NonNull
    protected <T> Observable<T> command(@NonNull CommandProvider<T, V> cmd) {
        PublishSubject<T> publishSubject = PublishSubject.create();
        commands.put(cmd, publishSubject);
        return publishSubject;
    }

    @SuppressWarnings("unchecked")
    private void subscribeIntents(@NonNull V view) {
        if (disposableCommands == null || disposableCommands.isDisposed()) {
            disposableCommands = new CompositeDisposable();
        }
        for (CommandProvider commandProvider : commands.keySet()) {
            Observable<Object> observable = commandProvider.provide(view);
            final PublishSubject<Object> subject = commands.get(commandProvider);
            disposableCommands.add(observable.subscribeWith(new DisposableObserver() {
                @Override
                public void onNext(@NonNull Object o) {
                    subject.onNext(o);
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    e.printStackTrace();
                    //no-op
                }

                @Override
                public void onComplete() {
                    System.out.println("Command completed!");
                    //no-op
                }
            }));
        }
    }

    /**
     * Is called every time there is a new state
     *
     * @param state the new state to be rendered
     * @param view  the which is bounded to this presenter
     */
    protected abstract void render(@NonNull S state, @NonNull V view);

    /**
     * Command provider interface
     *
     * @param <T> return type
     * @param <V> view type
     */
    protected interface CommandProvider<T, V> {
        @NonNull
        Observable<T> provide(@NonNull V view);
    }

    /**
     * @return Indicates if the presenter has subscribed to data stream, simply if
     * the {@link SlickPresenterUni#onViewUp(Object)} has ever called.
     */
    protected boolean hasSubscribed() {
        return hasSubscribed;
    }


    protected void dispose(@Nullable Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
