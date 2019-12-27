package com.moon.beautygirlkotlin.data.service

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.GirlRequestBody
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.entity.Source

/**
 * 简单工厂 + 策略
 * Created by Arthur on 2019-12-28.
 */
class DataRepository {


    //策略
    suspend fun getData(req: GirlRequestBody) : Result<List<GirlData>> {

        val service = getDataService(req.source)


        return service.getData(req)
    }


    //相当于工厂
    fun getDataService(source: Source): DataService =
            when(source) {


                "grank" -> GankService()

                else -> GankService()
            }

}

/*
* 也可以使用委托
* */

class ServiceFactory() {


}