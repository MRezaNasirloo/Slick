package com.github.slick.sample.di;

import java.util.Random;
import java.util.UUID;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : Pedramrn@gmail.com
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
