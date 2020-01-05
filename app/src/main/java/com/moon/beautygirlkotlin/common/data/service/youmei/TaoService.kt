package com.moon.beautygirlkotlin.common.data.service.youmei

import com.moon.beautygirlkotlin.common.data.entity.GirlData
import com.moon.beautygirlkotlin.common.data.entity.Result
import com.moon.beautygirlkotlin.common.data.service.DataService
import com.moon.beautygirlkotlin.common.data.service.weiyi.MeiZiTuBody
import org.jsoup.Jsoup
import java.net.URL
import java.util.*

/**
 * Created by Arthur on 2019-12-29.
 */
class TaoService: DataService {
    override suspend fun getData(page: Int, pageSize: Int, type: String?): Result<List<GirlData>> {

        val url = "http://www.umei.cc/bizhitupian/meinvbizhi/"

        val result =
            if (page == 1) {
                parserMeiTuLuHtml(url)
            } else {
                parserMeiTuLuHtml(url + page + ".htm")
            }


        return Result.Success(result.map {

            GirlData(UUID.randomUUID().toString(),it.url,it.title)
        })
    }

    /**
     * since 3.0
     *  http://www.umei.cc/bizhitupian/meinvbizhi/
     * 解析【优图美库】 html
     */
    fun parserMeiTuLuHtml(url: String): List<MeiZiTuBody> {
        val list = ArrayList<MeiZiTuBody>()
        val doc = Jsoup.parse(URL(url).openStream(), "UTF-8", url)
        val element = doc.select("div[class=TypeList]>ul>li>a[class=TypeBigPics]")

        for (e in element) {
            val imageUrl: String = e.select("img")[0].attr("src")
            val title: String = e.select("span")[0].html()

            // 将缩略图改为大图
            val imgList = imageUrl.split("/")
            val lastImg = imgList.last()
            if (lastImg.startsWith("rn")) {
                val newUrl = imageUrl.replace("rn", "")
                list.add(MeiZiTuBody(0, 354, 236, newUrl, newUrl, title, "", 0, 0))
            } else {
                list.add(MeiZiTuBody(0, 354, 236, imageUrl, imageUrl, title, "", 0, 0))
            }
        }
        return list
    }
}