package com.mrezanasirloo.slick.uni;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2018-03-11
 */

public class ViewStateExample {
    public ViewStateExample(String text, boolean liked) {
        this.text = text;
        this.liked = liked;
    }

    private String text;
    private boolean liked;

    public String text() {
        return text;
    }

    public boolean isLiked() {
        return liked;
    }

    @Override
    public String toString() {
        return "ViewStateExample{" +
                "text='" + text + '\'' +
                ", liked=" + liked +
                '}';
    }
}
