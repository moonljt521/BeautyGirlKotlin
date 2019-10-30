package com.moon.beautygirlkotlin.widget

import android.view.View
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody

/**
 * author: jiangtao.liang
 * date:   On 2019-10-30 20:31
 */
interface ItemClick {
    fun onClick(view: View , gankMeiziBody: GankMeiziBody)
}