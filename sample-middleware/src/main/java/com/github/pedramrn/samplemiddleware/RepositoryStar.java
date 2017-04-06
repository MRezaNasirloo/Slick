package com.github.pedramrn.samplemiddleware;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */
public class RepositoryStar {

    public Observable<Boolean> star(String id) {
        return Observable.just(true);
    }

    public Completable unStar(String id) {
        return Completable.complete();
    }
}
