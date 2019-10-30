package com.moon.beautygirlkotlin.utils

import com.moon.beautygirlkotlin.gank.GankModelFactory
import com.moon.beautygirlkotlin.gank.GankRepository

object InjectorUtil {

    fun getGankRepository() = GankRepository()

    fun getGankModelFactory() = GankModelFactory(getGankRepository())


}