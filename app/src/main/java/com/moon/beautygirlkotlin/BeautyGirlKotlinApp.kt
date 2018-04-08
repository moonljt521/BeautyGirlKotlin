package com.moon.beautygirlkotlin

import android.app.Application


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
    }

}