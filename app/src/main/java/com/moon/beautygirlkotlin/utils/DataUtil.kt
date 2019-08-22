package com.moon.beautygirlkotlin.utils

import com.google.gson.Gson
import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.huaban.model.HuaBanBody
import com.moon.beautygirlkotlin.huaban.model.HuaBanResp
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import retrofit2.Response
import java.net.URL
import java.util.*

/**
 * author: moon
 * created on: 18/4/29 下午10:22
 * description:
 */
object DataUtil {

    /**
     * 解析豆瓣 数据
     */
    fun getDouBanList(type: Int, response: Response<ResponseBody>?): List<DoubanMeiziBody> {

        Logger.i("3 = " + Thread.currentThread().name)

        val list = ArrayList<DoubanMeiziBody>()

        try {
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

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }


    /**
     * since 3.0
     * 解析【唯一图库】 html
     */
    fun parserMeiziTuHtml(url: String): List<MeiZiTuBody> {
        val list = ArrayList<MeiZiTuBody>()
        try {
            val doc = Jsoup.parse(URL(url).openStream(), "GB2312", url)
            val element = doc.select("div[class=item masonry_brick masonry-brick]>div[class=item_t]>div[class=img]>" +
                    "div[class=ABox]>a>img")
            for (e in element) {
                val imageUrl : String = e.attr("src")
                val title : String = e.attr("alt")
                val bean = MeiZiTuBody(0, 354, 236,imageUrl,imageUrl,title,"",0,0)
                list.add(bean)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return list
    }

    /**
     * since 3.0
     *
     * 解析【7160】 html
     */
    fun parserMeiTuLuHtml(url: String): List<MeiZiTuBody> {
        val list = ArrayList<MeiZiTuBody>()
        try {
            val doc = Jsoup.parse(URL(url).openStream(), "GB2312", url)
            val element : Elements = doc.getElementsByClass("news_bom-left")
            val datas = element.select("li[target=_blank]>img")

//            val element = doc.select("div[class=item masonry_brick masonry-brick]>div[class=item_t]>div[class=img]>" +
//                    "div[class=ABox]>a>img")
            for (e in datas) {
                val imageUrl : String = e.attr("src")
                val title : String = e.attr("alt")
                val bean = MeiZiTuBody(0, 354, 236,imageUrl,imageUrl,title,"",0,0)
                list.add(bean)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
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
        var list: ArrayList<HuaBanBody> = ArrayList()

        while (iterator.hasNext()) {
            val element = iterator.next()
            try {
                val bean: HuaBanBody = Gson().fromJson<HuaBanBody>(element.value, HuaBanBody::class.java)
                list.add(bean)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return list
    }


}