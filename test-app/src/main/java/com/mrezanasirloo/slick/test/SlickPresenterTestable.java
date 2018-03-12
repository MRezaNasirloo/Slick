package com.mrezanasirloo.slick.test;

import com.mrezanasirloo.slick.SlickPresenter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-10
 */

public class SlickPresenterTestable<V> extends SlickPresenter<V> {

    private AtomicInteger onViewUpCount = new AtomicInteger();
    private AtomicInteger onViewDownCount = new AtomicInteger();
    private AtomicInteger onDestroyCount = new AtomicInteger();

    @Override
    public void onViewUp(V view) {
        super.onViewUp(view);
        onViewUpCount.getAndIncrement();
    }

    @Override
    public void onViewDown() {
        super.onViewDown();
        onViewDownCount.getAndIncrement();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onDestroyCount.getAndIncrement();
    }

    public int onViewUpCount() {
        return onViewUpCount.get();
    }

    public int onViewDownCount() {
        return onViewDownCount.get();
    }

    public int onDestroyCount() {
        return onDestroyCount.get();
    }
}
