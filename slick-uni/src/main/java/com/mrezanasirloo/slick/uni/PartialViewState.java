package com.mrezanasirloo.slick.uni;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-07-14
 *
 *         This Interface is used to create partial data on every update
 */
public interface PartialViewState<T> {
    T reduce(T state);
}
