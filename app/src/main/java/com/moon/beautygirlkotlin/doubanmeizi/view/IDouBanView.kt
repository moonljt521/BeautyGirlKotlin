package com.moon.beautygirlkotlin.doubanmeizi.view

import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.mvpframework.view.BaseMvpView
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Response
import java.util.ArrayList


interface IDouBanView: BaseMvpView<List<DoubanMeiziBody>> {
}