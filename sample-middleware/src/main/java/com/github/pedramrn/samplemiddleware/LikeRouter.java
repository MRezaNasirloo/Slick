package com.github.pedramrn.samplemiddleware;

import com.github.slick.Middleware;
import com.github.slick.middleware.Callback;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

public class LikeRouter {

    private LikeRepository voteRepository = new LikeRepository();

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public String like(String id, Callback<String> stuff) {
        return voteRepository.like(id);
    }

    @Middleware({MiddlewareInternetAccess.class, MiddlewareLogin.class})
    public String unlike(String id,Callback<String> callback) {
        return voteRepository.unLike(id);
    }
}
