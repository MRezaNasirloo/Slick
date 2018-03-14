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

import android.content.SharedPreferences;

import com.mrezanasirloo.slick.middleware.BundleSlick;
import com.mrezanasirloo.slick.middleware.Middleware;
import com.mrezanasirloo.slick.middleware.Request;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-29
 */

public class MiddlewareLogin extends Middleware {


    private SharedPreferences sp;

    public MiddlewareLogin(SharedPreferences sp) {
        this.sp = sp;
    }

    @Override
    public void handle(Request request, BundleSlick date) {
        if (sp.getBoolean("HAS_LOGGED_IN", false)) {
            request.next();// process the next request
        } else {
            //            request.stopped();// optional, let the callback know about it
            Navigator.go(ActivityLogin.class);
        }
    }
}
