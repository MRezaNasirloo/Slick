package com.mrezanasirloo.slick.samplemiddleware;

import com.mrezanasirloo.slick.Middleware;
import com.mrezanasirloo.slick.middleware.Callback;

import io.reactivex.Single;

/**
 * @author : M.Reza.Nasirloo@gmail.com
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
