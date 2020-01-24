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

package com.mrezanasirloo.slick.sample.fragment.dagger;

import android.app.Fragment;

import com.mrezanasirloo.slick.sample.fragment.FragmentBaseTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.NonNull;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-10
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class FragmentDaggerTest extends FragmentBaseTest {

    /**
     * Test Presenter lifecycle
     */
    @Test
    public void testPresenter() {
        super.testPresenter();
    }


    @NonNull
    @Override
    protected Fragment createFragment() {
        return FragmentDagger.newInstance();
    }
}