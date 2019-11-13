package com.moon.beautygirlkotlin.my_favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.room.BeautyGirlDatabase
import com.moon.beautygirlkotlin.room.FavoriteBean

/**
 * author: jiangtao.liang
 * date:   On 2019-11-01 14:11
 */
class FavouriteVieModel : BaseViewModel(){

    var db: BeautyGirlDatabase;

    init {
        db = Room.databaseBuilder(
                BeautyGirlKotlinApp.application,
                BeautyGirlDatabase::class.java, "beauty_girl.db")
                .build()
    }

    var list = ArrayList<FavoriteBean>()

    val data = MutableLiveData<List<FavoriteBean>>().apply{
        value = emptyList()
    }

    fun getList() {

        launch({
            data.value = db.favouriteDao().getAll()

            list.clear()
            list.addAll(data.value!!)
        },{

        })
    }

    fun delItem(body:FavoriteBean){
        launch({
            db.favouriteDao().delete(body.id)
        },{})
    }

}