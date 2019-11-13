package com.moon.beautygirlkotlin.room;

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite")
data class FavoriteBean(
    @PrimaryKey var id: String = "",
    var title: String = "",
    var url: String = ""
)