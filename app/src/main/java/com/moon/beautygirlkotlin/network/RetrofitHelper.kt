package com.moon.beautygirlkotlin.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import com.moon.beautygirlkotlin.network.api.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * author: moon
 * created on: 18/4/25 下午3:03
 * description:
 */
object RetrofitHelper {

    init {
        initOkHttpClient();
    }


    private var mOkHttpClient: OkHttpClient? = null


    private val BASE_GANK_URL = "http://gank.io/api/"

    private val BASE_HUABAN_URL = "http://route.showapi.com/"

    private val BASE_DOUBAN_URL = "http://www.dbmeinv.com/dbgroup/"

    private val BASE_JIANDAN_URL = "http://jandan.net/"

    private val BASE_MEIZITU_URL = "http://www.mzitu.com/"
    /**
     * Gank妹子Api
     */
    fun getGankMeiziApi(): GankMeiziApi {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_GANK_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(GankMeiziApi::class.java)
    }


    /**
     * 豆瓣 Api
     */
    fun getDoubanMeiziApi(): DoubanMeizhiApi {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_DOUBAN_URL)
                .client(OkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(DoubanMeizhiApi::class.java)
    }



    /**
     * 花瓣Api
     */
    fun getHuaBanMeiziApi(): HuaBanMeiziApi {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_HUABAN_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        return retrofit.create(HuaBanMeiziApi::class.java)
    }


    /**
     * 淘女郎Api
     */
    fun getTaoFemaleApi(): TaoFemaleaApi {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_HUABAN_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(TaoFemaleaApi::class.java)
    }


//  /**
//   * 煎蛋Api
//   */
//  public static JianDanMeiziApi getJianDanApi() {
//
//    Retrofit retrofit = new Retrofit.Builder()
//        .baseUrl(BASE_JIANDAN_URL)
//        .client(mOkHttpClient)
//        .addConverterFactory(GsonConverterFactory.create())
//        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//        .build();
//
//    return retrofit.create(JianDanMeiziApi.class);
//  }


    /**
     * 获取妹子图Api
     */
    fun getMeiziTuApi(): MeiziTuApi {

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_MEIZITU_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(MeiziTuApi::class.java)
    }


    /**
     * 初始化OKHttpClient
     */
    private fun initOkHttpClient() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        if (mOkHttpClient == null) {
            synchronized(RetrofitHelper::class.java) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    val cache = Cache(File(BeautyGirlKotlinApp.application.cacheDir, "HttpCache"),
                            (1024 * 1024 * 100).toLong())

                    mOkHttpClient = OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addNetworkInterceptor(StethoInterceptor())
                            .retryOnConnectionFailure(true)
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .build()
                }
            }
        }
    }


}