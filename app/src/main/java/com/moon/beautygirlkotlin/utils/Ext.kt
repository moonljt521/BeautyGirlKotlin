package com.moon.beautygirlkotlin.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.CoroutineContext

/**
 * author: jiangtao.liang
 * date:   On 2019/8/19 18:12
 * des : 异步执行 扩展函数
 */

/**
 * 常规异步模式
 */
fun <T> executeRequest(context : CoroutineContext, request: suspend () -> T?, onSuccess: (T) -> Unit = {}, onFail: (Throwable) -> Unit = {}): Job {
    return CoroutineScope(Dispatchers.Main).launch(context) {
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

/**
 * async 模式
 */
fun <T> executeAsync(context : CoroutineContext, request: suspend () -> T?, onSuccess: (T) -> Unit = {}, onFail: (Throwable) -> Unit = {}): Deferred<Unit?> {
    return CoroutineScope(Dispatchers.Main).async(context) {
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

/**
 * 生产者 - 消费者模式
 */
@ExperimentalCoroutinesApi
fun <T> executeChannel(context : CoroutineContext,
                                  capacity : Int,
                                  request: suspend () -> T?, onSuccess: (T) -> Unit = {}, onFail: (Throwable) -> Unit = {}): ReceiveChannel<T> {
    return CoroutineScope(Dispatchers.Main).produce(context,capacity) {
        try {
            withContext(Dispatchers.IO) { request() }
        } catch (e: Exception) {
            e.printStackTrace()
            onFail(e)
        }
    }
}
