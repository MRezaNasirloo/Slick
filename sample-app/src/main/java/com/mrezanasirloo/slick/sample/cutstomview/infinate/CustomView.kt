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

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.mrezanasirloo.slick.Presenter
import com.mrezanasirloo.slick.SlickLifecycleListener
import com.mrezanasirloo.slick.SlickUniqueId

/**
 * @author : M.Reza.Nasirloo@gmail.com
 * Created on: 2017-03-09
 *
 * A multi instance Custom View
 */

class CustomView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs), ViewCustomView, SlickLifecycleListener, SlickUniqueId {

    @Presenter
    lateinit var presenter: PresenterCustomView

    private lateinit var idStr: String

    @SuppressLint("SetTextI18n")
    override fun onBind(instanceId: String) {
        idStr = instanceId
        PresenterCustomView_Slick.bind(this)
        PresenterCustomView_Slick.onAttach(this)
        text = "Presenter's code: ${presenter.code}, View's idStr: $uniqueId"
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        PresenterCustomView_Slick.onDetach(this)
    }

    override fun getUniqueId(): String {
        return idStr
    }
}
