package com.moon.beautygirlkotlin.utils

import com.google.gson.Gson
import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.huaban.model.HuaBanBody
import com.moon.beautygirlkotlin.huaban.model.HuaBanResp
import com.moon.beautygirlkotlin.meizitu.model.MeiZiTuBody
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
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


    /**
     * 解析妹子图html
     */
    fun parserMeiziTuHtml(html: String, type: String): List<MeiZiTuBody> {

        val list = ArrayList<MeiZiTuBody>()
        val doc = Jsoup.parse(html)
        val links = doc.select("li")

        var aelement: Element
        var imgelement: Element
        for (i in 7 until links.size) {

            imgelement = links[i].select("img").first()
            aelement = links[i].select("a").first()

            val url = aelement.attr("href")

            var bean = MeiZiTuBody(
                                0,
                                354,
                                236,
                                imgelement.attr("data-original"),
                                url,
                                imgelement.attr("alt"),
                                type,
                                url2groupid(url),
                                i)

            list.add(bean)
        }
        return list
    }

    /**
     * 获取妹子图的GroupId
     */
    private fun url2groupid(url: String): Int {

        return Integer.parseInt(url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[3])
    }


    /**
     * 解析json返回的数据 拼接为集合
     */
    fun getHuaBanList(json: String): List<HuaBanBody> {

        val result = Gson().fromJson<HuaBanResp>(json, HuaBanResp::class.java!!)
        val iterator = result.showapi_res_body.entrySet().iterator()
        var list : ArrayList<HuaBanBody> = ArrayList()

        while (iterator.hasNext()) {
            val element = iterator.next()
            try {
                var bean: HuaBanBody = Gson().fromJson<HuaBanBody>(element.value, HuaBanBody::class.java)

                list.add(bean)
            } catch (e: Exception) {

            }

        }

        return list
    }


}