package com.moon.beautygirlkotlin.common.utils

import android.util.Log

/**
 * author: moon
 * created on: 18/5/9 下午6:32
 * description:
 */
object Logger {


    val TAG = "BeautyGirlKotlin"

    var DEBUG = true

    fun i(log: String? = TAG, value: String) {
        if (DEBUG) {
            log?.let {
                Log.i(log, value)
            } ?:let {
                Log.i(TAG, value)
            }
        }
    }


    fun e(tag: String, value: String) {
        if (DEBUG) {
            Log.e(tag, value)
        }
    }

    fun d(value: String) {
        if (DEBUG) {
            Log.d(TAG, value)
        }

    }

    fun v(value: String) {
        if (DEBUG) {

            Log.v(TAG, value)
        }

    }

    fun e(value: String) {
        if (DEBUG) {

            Log.e(TAG, value)
        }
    }

}