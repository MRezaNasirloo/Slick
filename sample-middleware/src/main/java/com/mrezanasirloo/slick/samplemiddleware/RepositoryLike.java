package com.mrezanasirloo.slick.samplemiddleware;

import io.reactivex.Single;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */
public class RepositoryLike {

    public String like(String id) {
        return "Result something";
    }

    public Single<Boolean> unLike() {
        return Single.just(true);
    }
}
