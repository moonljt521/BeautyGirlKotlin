package com.moon.beautygirlkotlin.my_favorite.component

/**
 * Created by moon on 2017/9/8.
 */

interface ItemMoveListener {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemRemove(position: Int): Boolean
}
