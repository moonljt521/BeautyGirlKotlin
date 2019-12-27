package com.moon.beautygirlkotlin.data.service

import com.moon.beautygirlkotlin.data.entity.Source
import com.moon.beautygirlkotlin.data.service.ServiceFactory.Companion.getDataService

/**
 * 使用委托
 * * Created by Arthur on 2019-12-28.
 */
class DataServiceContext2(val source: Source )
    : DataService by getDataService(source) {


}
