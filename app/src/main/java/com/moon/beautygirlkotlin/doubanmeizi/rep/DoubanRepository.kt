package com.moon.beautygirlkotlin.doubanmeizi.rep

import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 20:04
 */
class DoubanRepository {


    suspend fun getDouBanMeiZiData(cid: Int, page: Int, type: Int): List<DoubanMeiziBody> {
        return withContext(Dispatchers.IO) {
            val result: Response<ResponseBody> = RetrofitHelper.getDoubanMeiziApi().getDoubanMeizi(cid, page)
            DataUtil.getDouBanList(type, result)
        }
    }
}