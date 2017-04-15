package com.github.pedramrn.samplemiddleware;

import com.github.slick.Middleware;
import com.github.slick.middleware.Callback;

import io.reactivex.Single;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

public class RouterLike {

    private RepositoryLike voteRepository = new RepositoryLike();

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public String like(String id, Callback<String> stuff) {
        return voteRepository.like(id);
    }

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public Single<Boolean> unlike() {
        return voteRepository.unLike();
    }
}
