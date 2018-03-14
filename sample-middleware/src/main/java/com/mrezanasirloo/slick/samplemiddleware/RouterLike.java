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
