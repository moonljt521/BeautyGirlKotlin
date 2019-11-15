package com.moon.beautygirlkotlin.youtumeiku.repository

import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 12:36
 */
class YouTuRepository {

    suspend fun getYoutuRepository(page: Int): List<MeiZiTuBody> {
        return getData(page)
    }
}

suspend fun getData(pageNum: Int): List<MeiZiTuBody> {
    val url = "http://www.umei.cc/bizhitupian/meinvbizhi/"

    return withContext(Dispatchers.IO) {
        if (pageNum == 1) {
            DataUtil.parserMeiTuLuHtml(url)
        } else {
            DataUtil.parserMeiTuLuHtml(url + pageNum + ".htm")
        }
    }
}
