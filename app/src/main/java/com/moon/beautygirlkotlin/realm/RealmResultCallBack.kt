package com.moon.beautygirlkotlin.realm

import com.moon.beautygirlkotlin.my_collect.model.MyCollectBody

/**
 * author: moon
 * created on: 18/5/18 下午12:37
 * description:
 */
interface RealmResultCallBack {

    fun findResult(list : List<MyCollectBody>)
    
}