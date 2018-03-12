package com.mrezanasirloo.slick.uni;

import io.reactivex.Observable;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-11
 */

public interface ViewExample {

    Observable<Integer> loadText();

    Observable<Boolean> likeComment();

    void setText(String text);

    void setLike(boolean liked);
}
