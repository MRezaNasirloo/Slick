package com.mrezanasirloo.slick.samplemiddleware;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author : M.Reza.Nasirloo@gmail.com
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
