package com.moon.beautygirlkotlin

import android.view.View
import android.widget.Checkable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
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
fun <T> executeRequest(context: CoroutineContext, request: suspend () -> T?, onSuccess: (T) -> Unit = {}, onFail: (Throwable) -> Unit = {}): Job {
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
fun <T> executeAsync(context: CoroutineContext, request: suspend () -> T?, onSuccess: (T) -> Unit = {}, onFail: (Throwable) -> Unit = {}): Deferred<Unit?> {
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
fun <T> executeChannel(context: CoroutineContext,
                       capacity: Int,
                       request: suspend () -> T?, onSuccess: (T) -> Unit = {}, onFail: (Throwable) -> Unit = {}): ReceiveChannel<T> {
    return CoroutineScope(Dispatchers.Main).produce(context, capacity) {
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

//val ExternalPresenter.presenterScope: CoroutineScope
//    get() {
//        val scope: CoroutineScope? = this.getTag(JOB_KEY)
//        return scope ?: setTagIfAbsent(
//                JOB_KEY,
//                PresenterCloseableCoroutineScope(SupervisorJob() + Dispatchers.Main)
//        )
//    }

internal class PresenterCloseableCoroutineScope(context: CoroutineContext) : Closeable, CoroutineScope {
    override val coroutineContext: CoroutineContext = context

    override fun close() {
        coroutineContext.cancel()
    }
}


inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
        provider: ViewModelProvider.Factory
) =
        ViewModelProviders.of(this, provider).get(VM::class.java)


/**
 * 防止重复点击
 */
var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

// 重复点击事件绑定
inline fun <T : View> T.singleClick(time: Long = 800, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}

/**
 * 防止重复点击eventObserver
 */
open class Event<T>(var view: View, var content: T) {

    var lastClickTime: Long
        get() = view.getTag(view.id) as? Long ?: 0
        set(value) = view.setTag(view.id, value)

    fun singleClick(): T? {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > 800) {
            lastClickTime = currentTimeMillis
            return content
        }
        return null
    }
}

class IntentObserver<T>(private val block: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.singleClick()?.let { value ->
            block(value)
        }
    }
}