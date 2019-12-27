package com.moon.beautygirlkotlin.data.service

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.GirlRequestBody
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.entity.Source
import com.moon.beautygirlkotlin.data.service.ServiceFactory.Companion.getDataService

/**
 * 简单工厂 + 策略
 * Created by Arthur on 2019-12-28.
 */
class DataServiceContext {


    //策略
    suspend fun getData(req: GirlRequestBody) : Result<List<GirlData>> {

        val service = getDataService(req.source)


        return service.getData(req)
    }


    //相当于工厂


}

/*
* 也可以使用委托
* */

class ServiceFactory() {

    companion object {

        fun getDataService(source: Source): DataService =
                when(source) {


                    "grank" -> GankService()

                    else -> GankService()
                }
    }

}