package com.moon.beautygirlkotlin.common.data.service.weiyi

import com.moon.beautygirlkotlin.common.data.entity.GirlData
import com.moon.beautygirlkotlin.common.data.entity.Result
import com.moon.beautygirlkotlin.common.data.service.DataService
import com.moon.beautygirlkotlin.common.network.RetrofitHelper
import org.jsoup.Jsoup
import java.net.URL
import java.util.*

/**
 * Created by Arthur on 2019-12-29.
 */
class OnlyDataService: DataService {
    override suspend fun getData(page: Int, pageSize: Int, type: String?): Result<List<GirlData>> {

      return Result.Success(parserMeiziTuHtml(
                RetrofitHelper.BASE_MEIZITU_URL + type + page + ".html").map {

          GirlData(UUID.randomUUID().toString(),it.imageurl,it.title)
      })
    }

    /**
     * since 3.0
     * 解析【唯一图库】 html
     */
    fun parserMeiziTuHtml(url: String): List<MeiZiTuBody> {
        val list = ArrayList<MeiZiTuBody>()
        val doc = Jsoup.parse(URL(url).openStream(), "GB2312", url)
        val element = doc.select("div[class=item masonry_brick masonry-brick]>div[class=item_t]>div[class=img]>" +
                "div[class=ABox]>a>img")
        for (e in element) {
            val imageUrl: String = e.attr("src")
            val title: String = e.attr("alt")
            val bean = MeiZiTuBody(0, 354, 236, imageUrl, imageUrl, title, "", 0, 0)
            list.add(bean)
        }
        return list
    }


}