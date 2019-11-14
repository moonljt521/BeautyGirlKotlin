package com.moon.beautygirlkotlin.my_favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.room.BeautyGirlDatabase
import com.moon.beautygirlkotlin.room.FavoriteBean
import com.moon.beautygirlkotlin.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * author: jiangtao.liang
 * date:   On 2019-11-01 14:11
 */
class FavouriteVieModel : BaseViewModel() {

    var db: BeautyGirlDatabase;

    init {
        db = Room.databaseBuilder(
                BeautyGirlKotlinApp.application,
                BeautyGirlDatabase::class.java, "beauty_girl.db")
                .build()
    }

    var list = ArrayList<FavoriteBean>()

    var size: Int = 0

    val data : MutableLiveData<List<FavoriteBean>> by lazy {
        MutableLiveData<List<FavoriteBean>>()
    }

    val total = MutableLiveData<Int>().apply {
        value = size
    }

    fun getList(page: Int) {

        launch({

            val result = withContext(Dispatchers.IO) {
                db.favouriteDao().queryByPage(page * 5)
            }
            if (page == 0) {
                list.clear()
            }

            list.addAll(result!!)

            data.value = result
        }, {
            it.printStackTrace()
        })
    }

    fun delItem(body: FavoriteBean) {
        launch({

            val size = withContext(Dispatchers.IO){
                db.favouriteDao().delete(body.id)
                db.favouriteDao().getAll()?.size!!
            }
            total.value = size
        }, {})
    }

    fun getTotalSize() {
        launch({
            total.value = db.favouriteDao().getAll()?.size!!
        }, {})
    }

}