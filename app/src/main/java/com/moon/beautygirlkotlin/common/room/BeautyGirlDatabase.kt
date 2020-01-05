package com.moon.beautygirlkotlin.common.room;

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(FavoriteBean::class), version = 1,exportSchema = false)
abstract class BeautyGirlDatabase : RoomDatabase() {

    abstract fun favouriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: BeautyGirlDatabase? = null

        fun getInstance(context: Context): BeautyGirlDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        BeautyGirlDatabase::class.java, "beauty_girl.db")
                        .build()
    }


}