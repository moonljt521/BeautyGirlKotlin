package com.moon.beautygirlkotlin.network

import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import com.moon.beautygirlkotlin.network.api.*
import com.moon.beautygirlkotlin.utils.Logger
import com.moon.beautygirlkotlin.utils.NetWorkUtil
import okhttp3.*
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
object RetrofitHelper: Interceptor {

    init {
        initOkHttpClient();
    }


    private var mOkHttpClient: OkHttpClient? = null

    // gank 妹子
    private val BASE_GANK_URL = "http://gank.io/api/"

    // 豆瓣
//    private val BASE_DOUBAN_URL = "http://www.dbmeinv.com/dbgroup/"
    private val BASE_DOUBAN_URL = "http://www.dbmeinv.com/"

    // 花瓣
    private val BASE_HUABAN_URL = "http://route.showapi.com/"


    // 煎蛋
    private val BASE_JIANDAN_URL = "http://jandan.net/"

    //妹子图
    private val BASE_MEIZITU_URL = "http://www.mzitu.com/"


    fun getRetroFitBuilder(url :String) : Retrofit {
        return Retrofit.Builder()
                .baseUrl(url)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    /**
     * Gank妹子Api
     */
    fun getGankMeiziApi(): GankMeiziAPI {
        return getRetroFitBuilder(BASE_GANK_URL).create(GankMeiziAPI::class.java)
    }


    /**
     * 豆瓣 Api
     */
    fun getDoubanMeiziApi(): DouBanAPI {
        return getRetroFitBuilder(BASE_DOUBAN_URL).create(DouBanAPI::class.java)
    }

    /**
     * 获取妹子图Api
     */
    fun getMeiziTuApi(): MeiziTuApi {
        return getRetroFitBuilder(BASE_MEIZITU_URL).create(MeiziTuApi::class.java)
    }

    /**
     * 花瓣Api
     */
    fun getHuaBanMeiziApi(): HuaBanAPI {
        return getRetroFitBuilder(BASE_HUABAN_URL).create(HuaBanAPI::class.java)
    }


    /**
     * 淘女郎Api
     */
    fun getTaoFemaleApi(): TaoFemaleaApi {
        return getRetroFitBuilder(BASE_HUABAN_URL).create(TaoFemaleaApi::class.java)
    }

    /**
     * 初始化OKHttpClient
     */
    private fun initOkHttpClient() {

//        val interceptor = HttpLoggingInterceptor()
//        if (Logger.DEBUG){
//            interceptor.level = HttpLoggingInterceptor.Level.HEADERS
//
//        }else{
//            interceptor.level = HttpLoggingInterceptor.Level.NONE
//        }

        var logInterceptor = HttpLoggingInterceptor(OkhttpLogInterceptor())
        if (Logger.DEBUG){
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        }else{
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        if (mOkHttpClient == null) {
            synchronized(RetrofitHelper::class.java) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
                    val cache = Cache(File(BeautyGirlKotlinApp.application.cacheDir, "HttpCache"),
                            (1024 * 1024 * 100).toLong())

                    mOkHttpClient = OkHttpClient.Builder()
                            .cache(cache)
                            // 自定义 拦截器 ，打印日志
//                            .addInterceptor(NetWorkInterceptor())
                            .addInterceptor(this)
                            .addInterceptor(logInterceptor)
//                            .addNetworkInterceptor(logInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .readTimeout(10,TimeUnit.SECONDS)
                            .writeTimeout(10,TimeUnit.SECONDS)
                            .build()
                }
            }
        }
    }

    /**
     * 设置网络缓存
     */
    override fun intercept(chain: Interceptor.Chain?): Response {
        val maxAge = 60 * 60 // 有网络时 设置缓存超时时间1个小时
        val maxStale = 60 * 60 * 24 * 1 // 无网络时，设置超时为1周
        var request = chain?.request()
        if (NetWorkUtil.isNetworkReachable(BeautyGirlKotlinApp.application)) {
            request = request?.newBuilder()
                    ?.cacheControl(CacheControl.FORCE_NETWORK)//有网络时只从网络获取
                    ?.removeHeader("User-Agent")
                    ?.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) ")
                    ?.build()
        } else {
            request = request?.newBuilder()
                    ?.cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                    ?.removeHeader("User-Agent")
                    ?.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) ")
                    ?.build()
        }

        var response = chain?.proceed(request)
        if (NetWorkUtil.isNetworkReachable(BeautyGirlKotlinApp.application)) {
            response = response?.newBuilder()
                    ?.removeHeader("Pragma")
                    ?.header("Cache-Control", "public, max-age=" + maxAge)
                    ?.build()
        } else {
            response = response?.newBuilder()
                    ?.removeHeader("Pragma")
                    ?.header("Cache-Control", "public, only-if-cached, max-stale=" +
                            maxStale)
                    ?.build()
        }
        return response!!
    }

}