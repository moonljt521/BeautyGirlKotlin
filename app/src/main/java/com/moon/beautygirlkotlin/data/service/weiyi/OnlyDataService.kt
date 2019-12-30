package com.moon.beautygirlkotlin.data.service.weiyi

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.service.DataService
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import java.util.*

/**
 * Created by Arthur on 2019-12-29.
 */
class OnlyDataService: DataService {
    override suspend fun getData(page: Int, pageSize: Int, type: String?): Result<List<GirlData>> {

      return Result.Success(DataUtil.parserMeiziTuHtml(
                RetrofitHelper.BASE_MEIZITU_URL + type + page + ".html").map {

          GirlData(UUID.randomUUID().toString(),it.imageurl,it.title)
      })
    }
}