package com.moon.beautygirlkotlin.base

/**
 * author: jiangtao.liang
 * date:   On 2019/8/19 18:13
 */
data class BaseResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)