package com.moon.beautygirlkotlin.common.utils

import android.content.Context
import android.content.Intent
import com.moon.beautygirlkotlin.R

/**
 * author: moon
 * created on: 18/4/28 下午2:28
 * description:
 */
class ShareUtil {

    companion object {
        fun shareAppLink(context: Context ,url: String, title: String ){
            try {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.share))
                intent.putExtra(Intent.EXTRA_TEXT, "google play:" + url)
                context.startActivity(Intent.createChooser(intent, title))
            }catch (e : Exception){}
        }
    }
}