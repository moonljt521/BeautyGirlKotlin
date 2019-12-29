package com.moon.beautygirlkotlin.data.service

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.GirlRequestBody
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.entity.Source
import com.moon.beautygirlkotlin.data.service.ServiceFactory.Companion.getDataService
import com.moon.beautygirlkotlin.data.service.douban.DoubanService
import com.moon.beautygirlkotlin.data.service.gank.GankService
import com.moon.beautygirlkotlin.data.service.weiyi.OnlyDataService
import com.moon.beautygirlkotlin.data.service.youmei.TaoService

/**
 * 简单使用 工厂 + 策略
 * Created by Arthur on 2019-12-28.
 */
class DataServiceContext {


    //策略
    suspend fun getData(req: GirlRequestBody): Result<List<GirlData>> {

        val service = getDataService(req.source)


        return service.getData(req.page,req.pageNum)
    }


}


class ServiceFactory() {

    companion object {

        fun getDataService(source: Source): DataService =
                when  {
                    source.isGank() -> GankService()
                    source.isDouban() -> DoubanService()
                    source.isWeiyi() -> OnlyDataService()
                    source.isTao() -> TaoService()
                    else -> GankService()
                }
    }

}