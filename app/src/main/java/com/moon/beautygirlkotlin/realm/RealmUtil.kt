package com.moon.beautygirlkotlin.realm

import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * author: moon
 * created on: 18/5/16 下午3:42
 * description:
 */
object RealmUtil {

    private val versionCode : Long = 1

   private lateinit var config: RealmConfiguration;

    fun init() {
        Realm.init(BeautyGirlKotlinApp.application)




        config = RealmConfiguration.Builder().name("kotlin_girl.realm")
                .schemaVersion(versionCode).migration(Migration).build()

        Realm.getInstance(config)

    }


    fun getRealm():Realm{

        return Realm.getInstance(config!!)


    }

}