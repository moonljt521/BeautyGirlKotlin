package com.moon.mvpframework.presenter

import android.support.annotation.MainThread

import java.io.Closeable
import java.io.IOException
import java.util.HashMap

/**
 * Created by Arthur on 2019-08-26.
 */
open class ExternalPresenter {

    private val mBagOfTags = HashMap<String, Any>()
    @Volatile
    private var mCleared = false

    protected fun onCleared() {

    }

    @MainThread
    public fun clear() {
        mCleared = true
        // Since clear() is final, this method is still called on mock objects
        // and in those cases, mBagOfTags is null. It'll always be empty though
        // because setTagIfAbsent and getTag are not final so we can skip
        // clearing it
        synchronized(mBagOfTags) {
            for (value in mBagOfTags.values) {
                // see comment for the similar call in setTagIfAbsent
                closeWithRuntimeException(value)
            }
        }
        onCleared()
    }

    fun <T> setTagIfAbsent(key: String, newValue: T): T {
        val previous: T?
        synchronized(mBagOfTags) {

            previous = mBagOfTags[key] as T?
            if (previous == null) {
                mBagOfTags[key] = newValue as Any
            }
        }
        val result = previous ?: newValue
        if (mCleared) {
            // It is possible that we'll call close() multiple times on the same object, but
            // Closeable interface requires close method to be idempotent:
            // "if the stream is already closed then invoking this method has no effect." (c)
            closeWithRuntimeException(result!!)
        }
        return result
    }

    /**
     * Returns the tag associated with this viewmodel and the specified key.
     */
    fun <T> getTag(key: String): T {

        synchronized(mBagOfTags) {
            return mBagOfTags[key] as T
        }
    }

    private fun closeWithRuntimeException(obj: Any) {
        if (obj is Closeable) {
            try {
                obj.close()
            } catch (e: IOException) {
                throw RuntimeException(e)
            }

        }
    }
}
