package com.mrezanasirloo.slick.sample.activity;


import com.mrezanasirloo.slick.test.SlickPresenterTestable;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-11-03
 */
public interface ViewTestable {
    SlickPresenterTestable<? extends ViewTestable> presenter();
}
