package com.github.pedramrn.samplemiddleware;

import com.github.slick.Middleware;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

public class RouterStar {

    private RepositoryStar repositoryStar = new RepositoryStar();

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public Observable<Boolean> star(String id) {
        return repositoryStar.star(id);
    }

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public Completable unStar(String id) {
        return repositoryStar.unStar(id);
    }
}
