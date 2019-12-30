package com.moon.beautygirlkotlin.data.test

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.service.DataServiceContext2
import com.moon.beautygirlkotlin.utils.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Arthur on 2019-12-28.
 */
class GirlRepository(private val service2: DataServiceContext2,private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

//   suspend fun getDatas(page: Int, pageNum: Int) = service2.getData(GirlRequestBody
//   ("",
//           page,pageNum))


    suspend fun getData(page: Int,pageSize: Int, type: String? = null): Result<List<GirlData>>  = withContext(ioDispatcher){

        Logger.d("GirlRepository getdata: type = ${type}")

        try {
            service2.getData(page,pageSize,type)

        }catch (e: Exception) {

            Result.Error(e)
        }

    }


}