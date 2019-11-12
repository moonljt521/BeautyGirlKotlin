package com.moon.beautygirlkotlin.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.moon.beautygirlkotlin.glide.GlideCircleTransForm

/**
 * author: moon
 * created on: 18/4/28 上午11:16
 * description:
 */
class ImageLoader {


    companion object {

        fun load(context: Context, url: String, view: ImageView) {

            Glide.with(context).load(url).into(view)

        }

        fun load(context: Context, url: String, placeholder: Int, view: ImageView) {
            if (!url.equals(view.tag)) {
                val requestOptions = RequestOptions().placeholder(placeholder)
                view.setTag(null)
                Glide.with(context).load(url).apply(requestOptions).into(view)
                view.setTag(url)
            }
        }

        fun loadImage(context: Context, url: String, placeHolder: Int, simpleTarget: SimpleTarget<Drawable>) {
            if (TextUtils.isEmpty(url)) {
                simpleTarget.onResourceReady(context.resources.getDrawable(placeHolder), null)
                return
            }
            val sCrossFade = DrawableTransitionOptions().crossFade(DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true))
            val requestOptions = RequestOptions().placeholder(placeHolder).error(placeHolder)
            Glide.with(context).load(url).apply(requestOptions).into(simpleTarget)
        }


        fun loadCircle(context: Context, url: Int, view: ImageView) {
            val requestOptions = RequestOptions().transform(GlideCircleTransForm(context))
            Glide.with(context).load(url).apply(requestOptions).into(view)
        }


    }


}