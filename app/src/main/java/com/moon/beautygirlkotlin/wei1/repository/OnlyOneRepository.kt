package com.moon.beautygirlkotlin.wei1.repository

import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 12:36
 */
class OnlyOneRepository {

    suspend fun getOnlyOneList(type: String, page: Int) : List<MeiZiTuBody>{
        return withContext(Dispatchers.IO){
            DataUtil.parserMeiziTuHtml(
                    RetrofitHelper.BASE_MEIZITU_URL + type + page + ".html")
        }
    }
}