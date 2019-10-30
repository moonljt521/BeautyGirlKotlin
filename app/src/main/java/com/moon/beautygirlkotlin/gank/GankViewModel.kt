package com.moon.beautygirlkotlin.gank

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.beautygirlkotlin.gank.model.GankMeiziResult
import kotlinx.coroutines.launch
import retrofit2.await

/**
 * author: jiangtao.liang
 * date:   On 2019-10-30 12:49
 */
class GankViewModel(private val repository : GankRepository) :ViewModel() {

    val list = ArrayList<GankMeiziBody>()

    val _item = MutableLiveData<List<GankMeiziBody>>().apply {
        value = emptyList()
    }

    var responseData = GankMeiziResult(ArrayList<GankMeiziBody>())

    fun getGankList(pageNum: Int, page: Int){
        viewModelScope.launch {
            _item.value = repository.getGankList(pageNum,page).results

            responseData.results.let { list.addAll(it) }
            return@launch
        }
    }

}