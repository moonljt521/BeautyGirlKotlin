package com.moon.beautygirlkotlin.realm

import com.moon.beautygirlkotlin.my_favorite.model.MyFavoriteBody

/**
 * author: moon
 * created on: 18/5/18 下午12:37
 * description:
 */
interface RealmResultCallBack {

    fun findResult(list : List<MyFavoriteBody>)
    
}