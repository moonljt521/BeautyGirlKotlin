package com.moon.beautygirlkotlin.data.service

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.GirlRequestBody
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.network.RetrofitHelper
import java.util.*

/**
 * Created by Arthur on 2019-12-28.
 */
class GankService : DataService {
    override suspend fun getData(req: GirlRequestBody): Result<List<GirlData>> {

        val result = RetrofitHelper.getGankMeiziApi().getGankMeizi(req.pageNum, req.page)

        return Result.Success(result.results.map {

            GirlData(UUID.randomUUID().toString(), it.url, it.desc, req.source)
        })
    }

}