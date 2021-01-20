package com.moon.beautygirlkotlin

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.android.gms.ads.MobileAds
import com.tencent.bugly.crashreport.CrashReport
import dagger.hilt.android.HiltAndroidApp


/**
 * application
 */
@HiltAndroidApp
class BeautyGirlKotlinApp: Application() {


    companion object {
        lateinit var application : Application

    }

    override fun onCreate() {
        super.onCreate()

        application = this

        //初始化google广告联盟 sdk
        MobileAds.initialize(this,"ca-app-pub-5604418926465302~4667836816")

        CrashReport.initCrashReport(getApplicationContext(), "1400017522", true);
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}