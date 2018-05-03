package com.moon.beautygirlkotlin.meizitu.model

/**
 * author: moon
 * created on: 18/5/3 上午10:41
 * description:  【妹子图】 bean
 */
data class MeiZiTuBody(var count: Int,
                       var width: Int,
                       var height: Int,
                       var imageurl: String,
                       var url: String,
                       var title: String,
                       var type: String,
                       var groupid: Int,
                       var order: Int) {
}