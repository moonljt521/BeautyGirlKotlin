package com.moon.beautygirlkotlin.network.api

import com.moon.beautygirlkotlin.youtumeiku.model.TaoFemale
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface TaoFemaleaApi {

    /**
     * 来自易源接口的淘女郎
     */
    @GET("126-2")
    fun getTaoFemale(@Query("page") page: Int,
                              @Query("showapi_appid") appId: String,
                              @Query("showapi_sign") sign: String): Observable<TaoFemale>

}