package com.moon.beautygirlkotlin.utils

import kotlinx.coroutines.*

/**
 * author: jiangtao.liang
 * date:   On 2019/8/19 18:12
 * des : 扩展函数
 */

fun <T> executeRequest4Gank(request: suspend () -> T?, onSuccess: (T) -> Unit = {}, onFail: (Throwable) -> Unit = {}): Job {
    val uiScope = CoroutineScope(Dispatchers.Main)
    return uiScope.launch {
        try {
            val res: T? = withContext(Dispatchers.IO) { request() }
            res?.let {
                onSuccess(it)
            }
        } catch (e: Exception) {
            onFail(e)
        }
    }
}

/**
 * onSuccess 方法 有可能切换线程 所以使用 了 suspend ，待优化 todo
 */
fun <T> executeRequest(request: suspend () -> T?, onSuccess: suspend (T) -> Unit = {}, onFail: (Throwable) -> Unit = {}): Job {
    val uiScope = CoroutineScope(Dispatchers.Main)
    return uiScope.launch {
        try {
            val res: T? = withContext(Dispatchers.IO) { request() }
            res?.let {
                onSuccess(it)
            }
        } catch (e: Exception) {
            onFail(e)
        }
    }
}


