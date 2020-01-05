package com.moon.beautygirlkotlin.common.data.service.douban

import com.moon.beautygirlkotlin.common.data.entity.GirlData
import com.moon.beautygirlkotlin.common.data.entity.Result
import com.moon.beautygirlkotlin.common.data.service.DataService
import com.moon.beautygirlkotlin.common.data.service.douban.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.common.network.RetrofitHelper
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Response
import java.util.*

/**
 * Created by Arthur on 2019-12-29.
 */
class DoubanService: DataService {
    override suspend fun getData(page: Int, pageSize: Int, type: String?): Result<List<GirlData>> {

        val cid =type?.toInt() ?: 0

        val result: Response<ResponseBody> = RetrofitHelper.getDoubanMeiziApi().getDoubanMeizi(cid,page)


        try {
            val list =   getDouBanList(1, result)

            return Result.Success( list.map {

                GirlData(UUID.randomUUID().toString(),it.url,it.title)
            })
        }catch (e: Exception) {

            return Result.Error(java.lang.Exception())
        }

    }

    /**
     * 解析豆瓣 数据
     */
    fun getDouBanList(type: Int, response: Response<ResponseBody>?): List<DoubanMeiziBody> {

        val list = ArrayList<DoubanMeiziBody>()

        if (response?.body() == null) throw java.lang.Exception()

        val string = response?.body()?.string()
        val parse = Jsoup.parse(string)
        val elements = parse.select("div[class=thumbnail]>div[class=img_single]>a>img")
        var meizi: DoubanMeiziBody
        for (e in elements) {
            val src = e.attr("src")
            val title = e.attr("title")

            meizi = DoubanMeiziBody(title, src, type)
            list.add(meizi)
        }
        return list
    }
}