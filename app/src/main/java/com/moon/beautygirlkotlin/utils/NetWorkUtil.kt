package com.moon.beautygirlkotlin.utils

import android.content.Context
import android.net.ConnectivityManager


/**
 * author: moon
 * created on: 18/4/30 下午10:27
 * description:
 */
class NetWorkUtil {

    companion object {

        /**
         * 判断网络是否可用
         *
         * @param context Context对象
         */
        fun isNetworkReachable(context: Context): Boolean {
            val cm = context
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val current = cm.activeNetworkInfo ?: return false
            return current.isAvailable
        }
    }
}