package com.moon.beautygirlkotlin.my_favorite

/**
 * author: jiangtao.liang
 * date:   On 2019-10-30 20:31
 */
interface FavouriteItemClick<T> {
    fun onClick(body: T)
}