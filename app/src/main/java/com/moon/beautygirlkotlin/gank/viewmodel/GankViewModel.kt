package com.moon.beautygirlkotlin.gank.viewmodel

import androidx.lifecycle.MutableLiveData
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.beautygirlkotlin.gank.repository.GankRepository

/**
 * author: jiangtao.liang
 * date:   On 2019-10-30 12:49
 */
class GankViewModel(private val repository: GankRepository) : BaseViewModel<GankMeiziBody>() {

    val _item : MutableLiveData<List<GankMeiziBody>> by lazy {
        MutableLiveData<List<GankMeiziBody>>()
    }

    fun getGankList(pageNum: Int, page: Int) {
        launch({
            val result = repository.getGankList(pageNum, page).results

            if (page == 1) {
                list.clear()
            }
            list.addAll(result)

            _item.value = result

            return@launch
        },
                {

                })
    }
}