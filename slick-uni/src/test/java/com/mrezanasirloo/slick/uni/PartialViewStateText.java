package com.mrezanasirloo.slick.uni;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-11
 */

public class PartialViewStateText implements PartialViewState<ViewStateExample> {

    private String text;

    public PartialViewStateText(String text) {
        this.text = text;
    }

    @Override
    public ViewStateExample reduce(ViewStateExample state) {
        return new ViewStateExample(text, state.isLiked());
    }
}
