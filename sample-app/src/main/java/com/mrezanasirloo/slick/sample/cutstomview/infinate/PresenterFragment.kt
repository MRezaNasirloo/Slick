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

package com.mrezanasirloo.slick.sample.cutstomview.infinate

import android.util.Log
import com.mrezanasirloo.slick.SlickPresenter
import java.util.*

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-04-13
 */
class PresenterFragment : SlickPresenter<ViewFragment>() {
    val code: Int = Random().nextInt(100 - 1) + 1

    override fun onViewUp(view: ViewFragment) {
        super.onViewUp(view)
        Log.d(TAG, "onViewUp() called: $code")
    }

    override fun onViewDown() {
        super.onViewDown()
        Log.d(TAG, "onViewDown() called: $code")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called: $code")
    }

    companion object {
        private val TAG = PresenterFragment::class.java.simpleName
    }
}