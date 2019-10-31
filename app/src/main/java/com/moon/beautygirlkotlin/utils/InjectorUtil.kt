package com.moon.beautygirlkotlin.utils

import com.moon.beautygirlkotlin.gank.GankModelFactory
import com.moon.beautygirlkotlin.gank.repository.GankRepository
import com.moon.beautygirlkotlin.wei1.OnlyOneModelFactory
import com.moon.beautygirlkotlin.wei1.repository.OnlyOneRepository

object InjectorUtil {

    private fun getGankRepository() = GankRepository()

    fun getGankModelFactory() = GankModelFactory(getGankRepository())



    private fun getOnlyOneRepository() = OnlyOneRepository()

    fun getOnlyOneModelFactory() = OnlyOneModelFactory(getOnlyOneRepository())

}