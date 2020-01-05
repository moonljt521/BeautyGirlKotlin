package com.moon.beautygirlkotlin.favorite.component

/**
 * Created by moon on 2017/9/8.
 */

interface ItemMoveListener {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemRemove(position: Int): Boolean

    fun canMove(position: Int) : Boolean
}
