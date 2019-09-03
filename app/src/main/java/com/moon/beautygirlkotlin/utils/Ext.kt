package com.moon.beautygirlkotlin.utils

import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.moon.mvpframework.presenter.ExternalPresenter
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import java.io.Closeable
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




/**
 * Created by Arthur on 2019-08-26.
 */
private const val JOB_KEY = "android.presenter.CoroutineScope.JOB_KEY"


val ExternalPresenter.presenterScope: CoroutineScope
    get() {
        val scope: CoroutineScope? = this.getTag(JOB_KEY)
        if (scope != null) {
            return scope
        }
        return setTagIfAbsent(
                JOB_KEY,
                PresenterCloseableCoroutineScope(SupervisorJob() + Dispatchers.Main)
        )
    }

internal class PresenterCloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}
