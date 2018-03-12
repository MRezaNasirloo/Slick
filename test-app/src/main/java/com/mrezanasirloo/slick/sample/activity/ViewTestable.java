package com.mrezanasirloo.slick.sample.activity;


import com.mrezanasirloo.slick.test.SlickPresenterTestable;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2016-11-03
 */
public interface ViewTestable {
    SlickPresenterTestable<? extends ViewTestable> presenter();
}
