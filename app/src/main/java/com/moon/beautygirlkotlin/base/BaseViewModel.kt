package com.moon.beautygirlkotlin.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 12:38
 */
open class BaseViewModel : ViewModel(){

    fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }


    fun async(block: suspend () -> Unit , error: suspend (Throwable) -> Unit) = viewModelScope.async {
        try {
            block()
        }catch (e : Throwable){
            error(e)
        }
    }
}





