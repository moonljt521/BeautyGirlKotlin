package com.moon.beautygirlkotlin

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport


/**
 * application
 */
class BeautyGirlKotlinApp: Application() {


    companion object {
        lateinit var application : Application

    }

    override fun onCreate() {
        super.onCreate()

        application = this

        CrashReport.initCrashReport(getApplicationContext(), "1400017522", true);
    }

}