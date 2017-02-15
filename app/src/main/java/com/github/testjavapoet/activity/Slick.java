package com.github.testjavapoet.activity;

import android.support.annotation.IdRes;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-12
 */

public class Slick {
    static void bind(ExampleActivity activity, @IdRes int id, String s) {
        ExampleActivityPresenter_Slick.bind(activity, id, s);
    }
}
