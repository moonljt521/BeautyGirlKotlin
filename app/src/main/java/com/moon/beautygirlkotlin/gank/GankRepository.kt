package com.moon.beautygirlkotlin.gank

import com.moon.beautygirlkotlin.network.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * author: jiangtao.liang
 * date:   On 2019-10-30 13:49
 */
class GankRepository () {

    suspend fun getGankList(pageNum: Int, page: Int) = withContext(Dispatchers.IO) {
        val response = RetrofitHelper.getGankMeiziApi().getGankMeizi(pageNum, page)
        response
    }
}