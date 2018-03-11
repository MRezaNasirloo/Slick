package com.github.pedramrn.slick.uni;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-11
 */

public class SlickPresenterUniExample extends SlickPresenterUni<ViewExample, ViewStateExample> {

    public SlickPresenterUniExample(Scheduler main, Scheduler io) {
        super(main, io);
    }

    @Override
    protected void start(ViewExample view) {
        Observable<PartialViewState<ViewStateExample>> like = command(ViewExample::likeComment)
                .flatMap(aBoolean -> Observable.just(aBoolean).subscribeOn(io))//call to backend
                .map(PartialViewStateLiked::new);

        Observable<PartialViewState<ViewStateExample>> loadText = command(ViewExample::loadText)
                .flatMap(aBoolean -> Observable.just("Foo Bar").subscribeOn(io))//call to backend
                .map(PartialViewStateText::new);

        reduce(new ViewStateExample(null, false), merge(like, loadText)).subscribe(this);

    }

    @Override
    protected void render(@NonNull ViewStateExample state, @NonNull ViewExample view) {
        System.out.println("state = [" + state + "]");
        if (state.text() != null) view.setText(state.text());
        view.setLike(state.isLiked());
    }
}
