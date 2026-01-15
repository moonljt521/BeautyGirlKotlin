package com.moon.beautygirlkotlin.favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.common.room.BeautyGirlDatabase
import com.moon.beautygirlkotlin.common.room.FavoriteBean
import com.moon.beautygirlkotlin.common.room.FavoriteBeanOther
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * author: jiangtao.liang
 * date:   On 2019-11-01 14:11
 */
@HiltViewModel
class FavouriteVieModel @Inject constructor(private  val db: BeautyGirlDatabase) :
        BaseViewModel<Any>() {

    var size: Int = 0

    val total = MutableLiveData<Int>().apply {
        value = size
    }

    fun getList(page: Int) {

        launch({

            val result = withContext(Dispatchers.IO) {
                db.favouriteDao().queryByPage(page * 10)
            }
            if (page == 0) {
                list.clear()
            }

            list.addAll(result!!)

            if (!list.isEmpty() && !result.isEmpty()){
                val showPage = page + 1
                list.add(FavoriteBeanOther(title = "第" + showPage + "页"))
            }

            data.postValue(result)

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
        })
    }

    fun getTotalSize() {
        launch({
            total.value = db.favouriteDao().getAll()?.size!!
        })
    }

}