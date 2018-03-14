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

package com.mrezanasirloo.slick.middleware;

import java.util.List;
import java.util.Stack;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-16
 */

public abstract class Request<P> {
    protected RequestStack routerStack = RequestStack.getInstance();
    protected P data;
    protected List<Middleware> middleware;
    protected BundleSlick bundleSlick;
    protected Stack<Middleware> middlewareStack = new Stack<>();
    protected int middlewareBackStack = 0;
    protected boolean tooLateAlreadyFinished = false;

    public abstract void next();

    protected void refill() {
        middlewareStack.clear();
        middlewareStack.addAll(middleware);
        middlewareBackStack = 0;
    }

    protected boolean hasPassed() {
        if (middlewareStack.size() > 0) {
            middlewareStack.pop().handle(this, bundleSlick == null ? (BundleSlick) data : bundleSlick);
        }
        middlewareBackStack++;
        return (middlewareBackStack - 1 == this.middleware.size() && !tooLateAlreadyFinished);
    }
}
