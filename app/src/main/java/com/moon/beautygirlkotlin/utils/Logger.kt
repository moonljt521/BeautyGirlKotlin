package com.moon.beautygirlkotlin.utils

import android.util.Log

/**
 * author: moon
 * created on: 18/5/9 下午6:32
 * description:
 */
object Logger {


    private val TAG = "moon"

    var DEBUG = true

    fun i(value: String) {
        if (DEBUG) {
            Log.i(TAG, value)

        }
    }

    fun i(tag: String, value: String) {
        if (DEBUG) {
            Log.i(tag, value)
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