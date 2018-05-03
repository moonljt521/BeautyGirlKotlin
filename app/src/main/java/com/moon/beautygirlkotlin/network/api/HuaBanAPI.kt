package com.moon.beautygirlkotlin.network.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * author: moon
 * created on: 18/5/3 下午4:58
 * description:
 */
interface HuaBanAPI {

    @GET("819-1")
    abstract fun getHuaBanMeizi(@Query("num") num: Int,
                                @Query("page") page: Int,
                                @Query("showapi_appid") appId: String,
                                @Query("type") type: Int,
                                @Query("showapi_sign") sign: String): Observable<ResponseBody>
}