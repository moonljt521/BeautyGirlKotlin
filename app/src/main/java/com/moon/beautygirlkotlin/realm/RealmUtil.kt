package com.moon.beautygirlkotlin.realm

import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import com.moon.beautygirlkotlin.my_favorite.model.MyFavoriteBody
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * author: moon
 * created on: 18/5/16 下午3:42
 * description:
 */
object RealmUtil {

    private val versionCode : Long = 1

    private lateinit var mRealm: Realm

   private lateinit var config: RealmConfiguration;

    fun init() {
        Realm.init(BeautyGirlKotlinApp.application)

        config = RealmConfiguration.Builder().name("kotlin_girl.realm")
                .schemaVersion(versionCode).migration(Migration).build()

        Realm.setDefaultConfiguration(config)

        mRealm = Realm.getDefaultInstance()

    }


    fun getRealm():Realm{
        return mRealm
    }


    // ---------------------------------

    /**
     * 获取 整个收藏列表 同步
     */
    fun getCollectAll():List<MyFavoriteBody> {

        val list = getRealm().where(MyFavoriteBody::class.java).findAll()

        return list!!
    }

    /**
     * 获取 整个收藏列表 异步
     */
    fun getCollectAllAsync(callBack: RealmResultCallBack) {

        var list: List<MyFavoriteBody>;

        getRealm().executeTransactionAsync({

            realm ->
            list =  Realm.getDefaultInstance().where(MyFavoriteBody::class.java).findAll()

            callBack.findResult(list)

        })

    }


    /**
     * 判断当前 图片是否收藏过
     */
    fun isCollected(url:String):Boolean {
        var result = getRealm().where(MyFavoriteBody::class.java).equalTo("url",url).findFirst()
        return result != null
    }


    /**
     * 添加一个收藏
     */
    fun addOneCollect(body: MyFavoriteBody) {
        getRealm().beginTransaction()

        getRealm().copyToRealmOrUpdate(body)

        getRealm().commitTransaction()
    }



    /**
     * 删除 一个收藏
     */
    fun delOneFavourite(body: MyFavoriteBody) {
        getRealm().beginTransaction()

        body.deleteFromRealm()

        getRealm().commitTransaction()
    }


}