package com.moon.beautygirlkotlin.common.listener



/**
 * author: jiangtao.liang
 * date:   On 2019-10-30 20:31
 */
interface ItemClick<T> {
    fun onClick(body: T)
}