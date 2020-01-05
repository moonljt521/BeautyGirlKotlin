package com.moon.beautygirlkotlin.common.utils

import com.moon.beautygirlkotlin.doubanmeizi.DoubanModelFactory
import com.moon.beautygirlkotlin.doubanmeizi.rep.DoubanRepository
import com.moon.beautygirlkotlin.gank.repository.GankModelFactory
import com.moon.beautygirlkotlin.gank.repository.GankRepository
import com.moon.beautygirlkotlin.wei1.OnlyOneModelFactory
import com.moon.beautygirlkotlin.wei1.repository.OnlyOneRepository
import com.moon.beautygirlkotlin.youtumeiku.repository.YouTuModelFactory
import com.moon.beautygirlkotlin.youtumeiku.repository.YouTuRepository

object InjectorUtil {

    private fun getGankRepository() = GankRepository()

    fun getGankModelFactory() = GankModelFactory(getGankRepository())


    private fun getOnlyOneRepository() = OnlyOneRepository()

    fun getOnlyOneModelFactory() = OnlyOneModelFactory(getOnlyOneRepository())


    private fun getDoubanRepository() = DoubanRepository()

    fun getDoubanModelFactory() = DoubanModelFactory(getDoubanRepository())


    private fun getYouTuRepository() = YouTuRepository()

    fun getYouTuModelFactory() = YouTuModelFactory(getYouTuRepository())


}