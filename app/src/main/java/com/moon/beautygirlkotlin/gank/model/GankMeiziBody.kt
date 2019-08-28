package com.moon.beautygirlkotlin.gank.model

import com.moon.beautygirlkotlin.base.BaseBean

data class GankMeiziBody(var _id : String
                         , var createdAt : String
                         , var desc :String
                         , var publishedAt :String
                         , var source :String
                         , var type :String
                         , var url :String
                         , var used :Boolean
                         , var who :String
                         , var itemType : Int) : BaseBean() {

    var scale : Float = 0f
}