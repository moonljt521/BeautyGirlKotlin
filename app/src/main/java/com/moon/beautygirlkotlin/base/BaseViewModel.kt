package com.moon.beautygirlkotlin.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.beautygirlkotlin.common.listener.ItemClick
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 12:38
 */
open class BaseViewModel<Any> : ViewModel(), ItemClick<Any> {

    override fun onClick(body: Any) {
        itemData.postValue(body)
    }

    val itemData: MutableLiveData<Any> by lazy {
        MutableLiveData<Any>()
    }


    val list = ArrayList<Any>()

    val data: MutableLiveData<List<Any>> by lazy {
        MutableLiveData<List<Any>>()
    }

    val errorData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun launch(block: suspend () -> Unit, error: (suspend (Throwable) -> Unit)? = null) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error?.invoke(e)
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





