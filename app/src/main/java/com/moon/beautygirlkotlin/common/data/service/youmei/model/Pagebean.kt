package com.moon.beautygirlkotlin.common.data.service.youmei.model

import com.moon.beautygirlkotlin.base.BaseBean

/**
 * author: moon
 * created on: 18/5/3 下午5:04
 * description:
 */
data class Pagebean(var allPages: String , var allNum : String , var currentPage : String , var maxResult : String,
                    var contentlist: ArrayList<Contentlist>) : BaseBean(){
}