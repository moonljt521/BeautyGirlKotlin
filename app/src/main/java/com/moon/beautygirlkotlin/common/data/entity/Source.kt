package com.moon.beautygirlkotlin.common.data.entity

import android.os.Parcelable
import com.moon.beautygirlkotlin.R
import kotlinx.android.parcel.Parcelize

/**
 * Created by Arthur on 2019-12-29.
 */

@Parcelize
data class Source(

        val id: Int,
        val title: Int,
        val types: List<SourceType>?

) : Parcelable{

        fun isGank() : Boolean = id == 0
        fun isDouban() : Boolean = id == 1
        fun isWeiyi() : Boolean = id == 2
        fun isTao() : Boolean = id == 3
}

/*
*
* */
@Parcelize
data class SourceType(
        val id: String,
        val title: String

): Parcelable




val doubanSource = Source(1,R.string.douban_meizi, listOf(
        SourceType("2", "大胸妹"),
        SourceType("6", "小翘臀"),
        SourceType("7", "黑丝袜"),
        SourceType("3", "美图控"),
        SourceType("4", "高颜值"),
        SourceType("5", "大杂烩")

))

val weiyiSource = Source(2,R.string.weiyi_pic,
        listOf(

                SourceType("list_18_", "明星相关专辑")
        )
)
val sourceList = listOf<Source>(

        Source(0,R.string.gank_meizi, null),

        doubanSource,
        weiyiSource,
        Source(3,R.string.tao_female, null)

 )


