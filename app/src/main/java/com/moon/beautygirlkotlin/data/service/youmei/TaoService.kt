package com.moon.beautygirlkotlin.data.service.youmei

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.service.DataService
import com.moon.beautygirlkotlin.utils.DataUtil
import java.util.*

/**
 * Created by Arthur on 2019-12-29.
 */
class TaoService: DataService {
    override suspend fun getData(page: Int, pageNum: Int, type: String?): Result<List<GirlData>> {

        val url = "http://www.umei.cc/bizhitupian/meinvbizhi/"

        val result =
            if (pageNum == 1) {
                DataUtil.parserMeiTuLuHtml(url)
            } else {
                DataUtil.parserMeiTuLuHtml(url + pageNum + ".htm")
            }


        return Result.Success(result.map {

            GirlData(UUID.randomUUID().toString(),it.url,it.title)
        })
    }
}