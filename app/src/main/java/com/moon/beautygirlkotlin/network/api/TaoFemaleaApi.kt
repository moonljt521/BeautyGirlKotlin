package com.moon.beautygirlkotlin.network.api

import com.moon.beautygirlkotlin.taofemale.TaoFemale
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface TaoFemaleaApi {

    /**
     * 来自易源接口的淘女郎
     */
    @FormUrlEncoded
    @GET("126-2")
    fun getTaoFemale(@Query("page") page: String,
                              @Query("showapi_appid") appId: String,
                              @Query("showapi_sign") sign: String): Observable<TaoFemale>

}