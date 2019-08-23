package com.moon.beautygirlkotlin.utils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * author: jiangtao.liang
 * date:   On 2019/8/19 18:12
 * des : 扩展函数
 */
fun <T> executeRequest(context : CoroutineContext, request: suspend () -> T?, onSuccess: (T) -> Unit = {}, onFail: (Throwable) -> Unit = {}): Job {
    val uiScope = CoroutineScope(Dispatchers.Main)
    return uiScope.launch(context) {
        try {
            val res: T? = withContext(Dispatchers.IO) { request() }
            res?.let {
                onSuccess(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onFail(e)
        }
    }
}


