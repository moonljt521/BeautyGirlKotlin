package com.moon.beautygirlkotlin.network


data class WanResponse<out T>(val errorCode: Int, val errorMsg: String, val data: T)