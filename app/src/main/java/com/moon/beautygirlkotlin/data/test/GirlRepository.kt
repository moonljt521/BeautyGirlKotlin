package com.moon.beautygirlkotlin.data.test

import com.moon.beautygirlkotlin.data.entity.GirlRequestBody
import com.moon.beautygirlkotlin.data.service.DataServiceContext2

/**
 * Created by Arthur on 2019-12-28.
 */
class GirlRepository(private val service2: DataServiceContext2) {

   suspend fun getDatas(page: Int, pageNum: Int) = service2.getData(GirlRequestBody
   ("",
           page,pageNum))


}