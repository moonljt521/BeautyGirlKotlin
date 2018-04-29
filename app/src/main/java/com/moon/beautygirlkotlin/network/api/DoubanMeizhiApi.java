package com.moon.beautygirlkotlin.network.api;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface DoubanMeizhiApi {

  /**
   * 根据cid查询不同类型的妹子图片
   */
  @GET("show.htm")
  Observable<Response<ResponseBody>> getDoubanMeizi(@Query("cid") int cid, @Query("pager_offset") int pageNum);
}
