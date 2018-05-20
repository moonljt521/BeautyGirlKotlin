package com.moon.beautygirlkotlin.my_favorite.component;

/**
 * Created by moon on 2017/9/8.
 */

public interface ItemMoveListener {
    boolean onItemMove(int fromPosition, int toPosition);

    boolean onItemRemove(int position);
}
