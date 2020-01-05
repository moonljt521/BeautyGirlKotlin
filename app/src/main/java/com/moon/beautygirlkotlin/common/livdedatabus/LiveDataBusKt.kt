package com.moon.beautygirlkotlin.common.livdedatabus

import java.util.HashMap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.Map

/**
 * author: jiangtao.liang
 * date:   On 2019-12-19 12:10
 */
class LiveDataBusKt private constructor() {

    private val bus: MutableMap<String, BusMutableLiveData<Any>>

    init {
        bus = HashMap()
    }

    fun <T> with(key: String, type: Class<T>): MutableLiveData<T> {
        if (!bus.containsKey(key)) {
            bus[key] = BusMutableLiveData()
        }
        return bus[key] as MutableLiveData<T>
    }

    fun with(key: String): MutableLiveData<Any> {
        return with(key, Any::class.java)
    }

    private class ObserverWrapper<T>(private val observer: Observer<T>?) : Observer<T> {

        private val isCallOnObserve: Boolean
            get() {
                val stackTrace = Thread.currentThread().stackTrace
                if (stackTrace != null && stackTrace.size > 0) {
                    for (element in stackTrace) {
                        if ("android.arch.lifecycle.LiveData" == element.className && "observeForever" == element.methodName) {
                            return true
                        }
                    }
                }
                return false
            }

        override fun onChanged(t: T?) {
            if (observer != null) {
                if (isCallOnObserve) {
                    return
                }
                observer.onChanged(t)
            }
        }
    }

    private class BusMutableLiveData<T> : MutableLiveData<T>() {

        private val observerMap = HashMap<Observer<in T>, Observer<in T>>()

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            try {
                hook(observer)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun observeForever(observer: Observer<in T>) {
            if (!observerMap.containsKey(observer)) {
                observerMap[observer] = ObserverWrapper(observer)
            }
            super.observeForever(observer)
        }

        override fun removeObserver(observer: Observer<in T>) {
            var realObserver: Observer<in T>? = null
            if (observerMap.containsKey(observer)) {
                realObserver = observerMap.remove(observer)
            } else {
                realObserver = observer
            }
            super.removeObserver(realObserver!!)
        }

        @Throws(Exception::class)
        private fun hook(observer: Observer<in T>) {
            //get wrapper's version
            val classLiveData = LiveData::class.java
            val fieldObservers = classLiveData.getDeclaredField("mObservers")
            fieldObservers.isAccessible = true
            val objectObservers = fieldObservers.get(this)
            val classObservers = objectObservers.javaClass
            val methodGet = classObservers.getDeclaredMethod("get", Any::class.java)
            methodGet.isAccessible = true
            val objectWrapperEntry = methodGet.invoke(objectObservers, observer)
            var objectWrapper: Any? = null
            if (objectWrapperEntry is Map.Entry<*, *>) {
                objectWrapper = objectWrapperEntry.value
            }
            if (objectWrapper == null) {
                throw NullPointerException("Wrapper can not be bull!")
            }
            val classObserverWrapper : Class<in Any>?  = objectWrapper.javaClass.superclass
            val fieldLastVersion = classObserverWrapper?.getDeclaredField("mLastVersion")
            fieldLastVersion?.isAccessible = true
            //get livedata's version
            val fieldVersion = classLiveData.getDeclaredField("mVersion")
            fieldVersion.isAccessible = true
            val objectVersion = fieldVersion.get(this)
            //set wrapper's version
            fieldLastVersion?.set(objectWrapper, objectVersion)
        }
    }

    companion object {

        @Volatile
        private var instance: LiveDataBusKt? = null

        fun get(): LiveDataBusKt? {
            if (instance == null) {
                synchronized(LiveDataBusKt::class.java) {
                    if (instance == null) {
                        instance = LiveDataBusKt()
                    }
                }
            }
            return instance
        }
    }
}
