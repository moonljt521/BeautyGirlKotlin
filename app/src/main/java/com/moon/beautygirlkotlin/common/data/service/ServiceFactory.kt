package com.moon.beautygirlkotlin.common.data.service

import com.moon.beautygirlkotlin.common.data.entity.Source
import com.moon.beautygirlkotlin.common.data.service.douban.DoubanService
import com.moon.beautygirlkotlin.common.data.service.gank.GankService
import com.moon.beautygirlkotlin.common.data.service.weiyi.OnlyDataService
import com.moon.beautygirlkotlin.common.data.service.youmei.TaoService

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