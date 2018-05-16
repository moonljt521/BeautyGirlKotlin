package com.moon.beautygirlkotlin.my_collect.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * author: moon
 * created on: 18/5/16 下午2:09
 * description: 我的收藏实体
 */
@RealmClass
open class MyCollectBody : RealmObject(){

    @PrimaryKey
    open var id :String = ""

    open var url :String =""

    open var title : String = ""
}