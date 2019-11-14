package com.moon.beautygirlkotlin.base

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.utils.ImageLoader

/**
 * author: jiangtao.liang
 * date:   On 2019-10-30 19:44
 */

@BindingAdapter("bind:imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    imageView.setScaleType(ImageView.ScaleType.CENTER);
    imageView.setImageResource(R.drawable.default_icon)
    ImageLoader.loadImage(imageView.context, url, R.drawable.default_icon, object : SimpleTarget<Drawable>() {
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setImageDrawable(resource)
        } }
    )

}



