package com.moon.beautygirlkotlin.common.network

import com.moon.beautygirlkotlin.common.utils.Logger
import okhttp3.logging.HttpLoggingInterceptor

/**
 * author: moon
 * created on: 18/5/18 上午10:08
 * description:
 */
class OkhttpLogInterceptor: HttpLoggingInterceptor.Logger {


    override fun log(message: String?) {

        Logger.i(Logger.TAG, message!!)

    }
}