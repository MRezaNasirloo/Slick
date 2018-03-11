package com.github.pedramrn.slick.uni;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2018-03-11
 */

public class PartialViewStateLiked implements PartialViewState<ViewStateExample> {

    private boolean isLiked;

    public PartialViewStateLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    @Override
    public ViewStateExample reduce(ViewStateExample state) {
        return new ViewStateExample(state.text(), isLiked);
    }
}
