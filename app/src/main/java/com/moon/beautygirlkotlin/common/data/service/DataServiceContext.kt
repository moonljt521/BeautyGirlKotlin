package com.moon.beautygirlkotlin.common.data.service

import com.moon.beautygirlkotlin.common.data.entity.Source
import com.moon.beautygirlkotlin.common.data.service.ServiceFactory.Companion.getDataService

/**
 * 使用委托
 * * Created by Arthur on 2019-12-28.
 */
class DataServiceContext(val source: Source )
    : DataService by getDataService(source) {


}
