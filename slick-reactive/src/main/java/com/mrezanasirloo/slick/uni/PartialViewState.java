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
 *         Created on: 2017-07-14
 *
 *         This Interface is used to create partial data update
 */
public interface PartialViewState<T> {
    /**
     * @param state the old state
     * @return the new state that should be rendered to view
     */
    @NonNull
    T reduce(@NonNull T state);
}
