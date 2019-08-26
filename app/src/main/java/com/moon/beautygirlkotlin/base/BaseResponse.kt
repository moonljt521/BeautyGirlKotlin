package com.moon.beautygirlkotlin.base

/**
 * author: jiangtao.liang
 * date:   On 2019/8/26 12:03
 */
open class BaseResponse<T>(){
    var error :Boolean = false
    var t: T? = null
}
