package com.moon.beautygirlkotlin.common.room;

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite")
data class FavoriteBean(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        var title: String = "",
        var url: String = "",
        var createTime: Long = 0
)