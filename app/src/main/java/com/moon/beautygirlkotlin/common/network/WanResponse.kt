package com.moon.beautygirlkotlin.common.network


data class WanResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)