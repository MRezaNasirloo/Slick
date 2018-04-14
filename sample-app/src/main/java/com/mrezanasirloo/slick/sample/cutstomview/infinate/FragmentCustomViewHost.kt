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
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mrezanasirloo.slick.Presenter
import com.mrezanasirloo.slick.SlickDelegateActivity
import com.mrezanasirloo.slick.SlickUniqueId
import com.mrezanasirloo.slick.sample.R
import kotlinx.android.synthetic.main.fragment_custom_view_host.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 *
 */
private const val ARG_1 = "ARG_1"

class FragmentCustomViewHost : Fragment(), SlickUniqueId, ViewFragment {

    private lateinit var id: String
    private lateinit var pos: String
    @Presenter
    lateinit var presenter: PresenterFragment

    companion object {

        var counter: Int = 0
        @JvmStatic
        fun newInstance(pos: String): FragmentCustomViewHost =
                FragmentCustomViewHost().apply {
                    arguments = Bundle().apply {
                        putString(ARG_1, pos)
                    }
                }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getString(SlickDelegateActivity.SLICK_UNIQUE_KEY)?.let { id = it }
        // Only call bind after you have an Id, otherwise you end up with a new instance of your presenter every time
        PresenterFragment_Slick.bind(this)

        arguments?.let {
            pos = it.getString(ARG_1)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_custom_view_host, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Here I used the id of this fragment, I you have multiple instance of your custom view within the same parent
        // you should use different id for each of them.
        custom_view_in_fragment.onBind(uniqueId)
//        custom_view_in_fragment2.onBind(uniqueId + 2)
//        custom_view_in_fragment3.onBind(uniqueId + 3)
        button_next.text = pos
        text_view_info.text = "Presenter's code: ${presenter.code}, View's id: $uniqueId"
        button_next.setOnClickListener { _ ->
            counter++
            fragmentManager?.apply {
                beginTransaction()
                        .replace(R.id.container, FragmentCustomViewHost.newInstance(counter.toString()))
                        .addToBackStack(null)
                        .commit()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SlickDelegateActivity.SLICK_UNIQUE_KEY, id)
    }

    override fun onDestroy() {
        PresenterCustomView_Slick.onDestroy(uniqueId, activity)
//        PresenterCustomView_Slick.onDestroy(uniqueId + 2, activity)
//        PresenterCustomView_Slick.onDestroy(uniqueId + 3, activity)
        super.onDestroy()
    }

    override fun getUniqueId(): String {
        if (this::id.isInitialized) {
            return id
        }
        id = UUID.randomUUID().toString()
        return id
    }

}
