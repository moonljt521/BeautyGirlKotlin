package com.moon.beautygirlkotlin.network.api

import com.moon.beautygirlkotlin.gank.model.GankMeiziResult
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * author: moon
 * created on: 18/5/3 下午5:01
 * description:
 */
interface GankMeiziAPI {

    /**
     * gank妹子,福利
     */
//    @GET("data/福利/{number}/{page}")
//    fun getGankMeizi(@Path("number") number: Int, @Path("page") page: Int): Observable<GankMeiziResult>

    @GET("data/福利/{number}/{page}")
    suspend fun getGankMeizi(@Path("number") number: Int, @Path("page") page: Int): GankMeiziResult

}