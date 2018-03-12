package com.mrezanasirloo.slick.sample.activity.dagger;

import java.util.Random;

import dagger.Module;
import dagger.Provides;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-02
 */
@Module
public class DaggerModule {

    @Provides
    public Long code() {
        return new Random().nextLong();
    }
}
