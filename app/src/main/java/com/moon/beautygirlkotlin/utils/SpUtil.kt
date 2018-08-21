package com.moon.beautygirlkotlin.utils

import android.content.Context
import com.moon.beautygirlkotlin.BeautyGirlKotlinApp

/**
 * author: moon
 * created on: 18/5/18 下午2:55
 * description:
 */
object SpUtil {

    var share = BeautyGirlKotlinApp.application.getSharedPreferences("com.moon.beautygirlkotlin", Context
            .MODE_PRIVATE)

    fun tipSwipeDelFavourite():Boolean{

        val sp = share.getBoolean("hastip",false)

        if (!sp){

            val edit = share.edit()
            edit.putBoolean("hastip",true)
            edit.commit()
        }
        return sp
    }

}