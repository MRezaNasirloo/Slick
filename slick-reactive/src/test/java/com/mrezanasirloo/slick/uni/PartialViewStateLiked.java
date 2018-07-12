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

package com.mrezanasirloo.slick.uni;

import android.support.annotation.NonNull;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-11
 */

public class PartialViewStateLiked implements PartialViewState<ViewStateExample> {

    private boolean isLiked;

    public PartialViewStateLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    @NonNull
    @Override
    public ViewStateExample reduce(@NonNull ViewStateExample state) {
        return new ViewStateExample(state.text(), isLiked);
    }
}
