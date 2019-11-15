package com.moon.beautygirlkotlin.network

import com.moon.beautygirlkotlin.utils.Logger
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