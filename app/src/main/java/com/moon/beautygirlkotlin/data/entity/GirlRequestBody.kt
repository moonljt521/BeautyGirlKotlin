package com.moon.beautygirlkotlin.data.entity

/**
 * Created by Arthur on 2019-12-28.
 */
//typealias Source = String

///简单定义一下，
// 也可以不使用这个，
// 直接传值即可
data class GirlRequestBody(

    val source: Source,

    val page: Int,

    val pageNum: Int

)