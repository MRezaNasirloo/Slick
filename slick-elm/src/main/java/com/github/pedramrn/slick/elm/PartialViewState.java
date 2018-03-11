package com.github.pedramrn.slick.elm;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-07-14
 */

public interface PartialViewState<T> {
    T reduce(T state);
}
