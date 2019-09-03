package com.moon.beautygirlkotlin.utils

import android.content.Context
import android.content.Intent

/**
 * author: moon
 * created on: 18/4/28 下午2:28
 * description:
 */
class ShareUtil {

    companion object {
        fun shareAppLink(context: Context ,url: String, title: String ){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享")
            intent.putExtra(Intent.EXTRA_TEXT, "google play:" + url)
            context.startActivity(Intent.createChooser(intent, title))
        }
    }
}