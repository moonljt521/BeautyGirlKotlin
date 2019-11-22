package com.moon.beautygirlkotlin.gank.repository

import com.moon.beautygirlkotlin.network.RetrofitHelper

/**
 * author: jiangtao.liang
 * date:   On 2019-10-30 13:49
 */
class GankRepository () {

    suspend fun getGankList(pageNum: Int, page: Int) = RetrofitHelper.getGankMeiziApi().getGankMeizi(pageNum, page)
}