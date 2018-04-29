package com.moon.beautygirlkotlin.utils

import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Response
import java.util.ArrayList

/**
 * author: moon
 * created on: 18/4/29 下午10:22
 * description:
 */
object DataUtil {

    /**
     * 解析豆瓣 数据
     */
    fun getDouBanList(type: Int, response: Response<ResponseBody>): List<DoubanMeiziBody> {

        val list = ArrayList<DoubanMeiziBody>()

        try {
            val string = response.body().string()
            val parse = Jsoup.parse(string)
            val elements = parse.select("div[class=thumbnail]>div[class=img_single]>a>img")
            var meizi: DoubanMeiziBody
            for (e in elements) {
                val src = e.attr("src")
                val title = e.attr("title")

                meizi = DoubanMeiziBody(title, src, type)

                list.add(meizi)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }

}