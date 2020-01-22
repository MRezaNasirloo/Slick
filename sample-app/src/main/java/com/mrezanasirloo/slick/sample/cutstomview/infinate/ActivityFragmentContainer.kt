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

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mrezanasirloo.slick.sample.R
import kotlinx.android.synthetic.main.activity_fragment_container.*

class ActivityFragmentContainer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        val fm = supportFragmentManager
        if (fm.findFragmentByTag("ROOT") == null) {
            fm.beginTransaction()
                    .replace(R.id.container, FragmentCustomViewHost.newInstance(FragmentCustomViewHost.counter.toString()), "ROOT")
                    .addToBackStack("ROOT")
                    .commit()
        }

        button_pop.setOnClickListener {
            fm.popBackStack("ROOT", 0)
            FragmentCustomViewHost.counter = 0
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
            return
        }
        super.onBackPressed()
        FragmentCustomViewHost.counter--
    }
}
