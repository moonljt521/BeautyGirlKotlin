package com.moon.beautygirlkotlin.my_favorite.viewmodel

import androidx.lifecycle.MutableLiveData
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.my_favorite.model.MyFavoriteBody
import com.moon.beautygirlkotlin.realm.RealmUtil

/**
 * author: jiangtao.liang
 * date:   On 2019-11-01 14:11
 */
class FavouriteVieModel : BaseViewModel(){

    var list = ArrayList<MyFavoriteBody>()

    val data = MutableLiveData<List<MyFavoriteBody>>().apply{
        value = emptyList()
    }

    fun getList() {

        launch({
            data.value = RealmUtil.getCollectAll()

            list.clear()
            list.addAll(data.value!!)
        },{

        })
    }

}