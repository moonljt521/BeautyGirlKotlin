package com.moon.beautygirlkotlin.network.api;



import com.moon.beautygirlkotlin.mengmeizi.model.GankMeiziResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GankMeiziApi {

  /**
   * gank妹子,福利
   */
  @GET("data/福利/{number}/{page}")
  Observable<GankMeiziResult> getGankMeizi(@Path("number") int number, @Path("page") int page);
}
