package com.moon.beautygirlkotlin.doubanmeizi.viewmodel

import androidx.lifecycle.MutableLiveData
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.doubanmeizi.rep.DoubanRepository

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 20:03
 */
class DoubanViewModel(private val repository: DoubanRepository) : BaseViewModel() {

    var list = ArrayList<DoubanMeiziBody>()

    var data = MutableLiveData<List<DoubanMeiziBody>>().apply {
        value = emptyList()
    }
    fun getList(cid: Int, page: Int, type: Int) {
        launch({
            data.value = repository.getDouBanMeiZiData(cid, page, type)

            if (!data.value?.isEmpty()!!){
                list.addAll(data.value!!)
            }

        }, {

        })

    }
}