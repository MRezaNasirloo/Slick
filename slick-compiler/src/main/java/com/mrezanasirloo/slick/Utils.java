package com.mrezanasirloo.slick;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-22
 */

public class Utils {
    public static String deCapitalize(String string) {
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }
}
