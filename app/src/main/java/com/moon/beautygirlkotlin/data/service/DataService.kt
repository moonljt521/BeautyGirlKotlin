package com.moon.beautygirlkotlin.data.service

import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.GirlRequestBody
import com.moon.beautygirlkotlin.data.entity.Result

/**
 * Created by Arthur on 2019-12-28.
 */

interface DataService {

    suspend fun getData(req: GirlRequestBody) : Result<List<GirlData>>
}