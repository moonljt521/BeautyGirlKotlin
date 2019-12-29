package com.moon.beautygirlkotlin.data.service.gank

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.service.DataService
import com.moon.beautygirlkotlin.network.RetrofitHelper
import java.util.*

/**
 * Created by Arthur on 2019-12-28.
 */
class GankService : DataService {
    override suspend fun getData(page: Int, pageNum: Int, type: String?): Result<List<GirlData>> {
        val result = RetrofitHelper.getGankMeiziApi().getGankMeizi(pageNum, page)

        if(result.error) {

            return Result.Error(Exception(""))

        }else {
            return Result.Success(result.results.map {

                GirlData(UUID.randomUUID().toString(), it.url, it.desc)
            })

        }
    }

}