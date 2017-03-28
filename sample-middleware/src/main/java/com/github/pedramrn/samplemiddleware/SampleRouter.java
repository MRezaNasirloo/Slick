package com.github.pedramrn.samplemiddleware;

import com.github.slick.Middleware;
import com.github.slick.middleware.Callback;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

public class SampleRouter {

    private VoteRepository voteRepository = new VoteRepository();

    @Middleware(SampleMiddleware.class)
    public Boolean voteUp(String id, Callback<Boolean> stuff) {
        return voteRepository.voteUp(id);
    }

}
