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

package com.mrezanasirloo.slick.sample.di;

import java.util.Random;
import java.util.UUID;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-02
 */
@Module
public class AppModule {

    @Singleton
    @Provides
    public String string() {
        return UUID.randomUUID().toString();
    }

    @Singleton
    @Provides
    public Integer integer() {
        return new Random().nextInt();
    }
}
