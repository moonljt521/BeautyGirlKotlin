package com.moon.beautygirlkotlin.data.service.douban

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.service.DataService
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.*

/**
 * Created by Arthur on 2019-12-29.
 */
class DoubanService: DataService {
    override suspend fun getData(page: Int, pageNum: Int, type: String?): Result<List<GirlData>> {

        val cid =type?.toInt() ?: 0

        val result: Response<ResponseBody> = RetrofitHelper.getDoubanMeiziApi().getDoubanMeizi(cid,pageNum)


        try {
            val list =   DataUtil.getDouBanList(1, result)

            return Result.Success( list.map {

                GirlData(UUID.randomUUID().toString(),it.url,it.title)
            })
        }catch (e: Exception) {

            return Result.Error(java.lang.Exception())
        }

    }
}