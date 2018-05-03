package com.moon.beautygirlkotlin.taofemale.model

/**
 * author: moon
 * created on: 18/5/3 下午5:06
 * description:
 */
data class Contentlist(var totalFavorNum : Int , var realName: String,
                       var totalFanNum : String , var link: String,
                       var weight : String , var avatarUrl : String,
                       var type : String , var userId :String,
                       var city : String , var height :String,
                       var imgList : ArrayList<String> , var cardUrl :String) {
}