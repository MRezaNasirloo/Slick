package com.github.slick;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */

public class BasePresenterGenerator {
    protected String unCapitalize(String string) {
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }
}
