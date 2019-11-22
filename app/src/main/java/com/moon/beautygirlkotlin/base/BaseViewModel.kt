package com.moon.beautygirlkotlin.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.beautygirlkotlin.listener.ItemClick
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 12:38
 */
open class BaseViewModel<T> : ViewModel(), ItemClick<T> {

    override fun onClick(body: T) {
        itemData.postValue(body)
    }

    val itemData: MutableLiveData<T> by lazy {
        MutableLiveData<T>()
    }


    val list = ArrayList<T>()

    val data: MutableLiveData<List<T>> by lazy {
        MutableLiveData<List<T>>()
    }

    val errorData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
            errorData.postValue(0)
        }
    }

    fun async(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.async {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
            errorData.postValue(0)
        }
    }
}





