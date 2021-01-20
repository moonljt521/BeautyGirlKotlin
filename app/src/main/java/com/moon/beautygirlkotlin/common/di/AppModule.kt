package com.moon.beautygirlkotlin.common.di

import android.content.Context
import androidx.room.Room
import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import com.moon.beautygirlkotlin.common.room.BeautyGirlDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by Arthur on 2021/1/20.
 */
@InstallIn(ApplicationComponent::class)
@Module
class AppModule  {


    @Singleton
    @Provides
    fun provideBeautyGirlDatabase(@ApplicationContext context: Context): BeautyGirlDatabase {

        return Room.databaseBuilder(
                context,
                BeautyGirlDatabase::class.java, "beauty_girl.db")
                .build()
    }

}