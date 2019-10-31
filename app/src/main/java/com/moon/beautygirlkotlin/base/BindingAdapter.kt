package com.moon.beautygirlkotlin.base

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.utils.ImageLoader

/**
 * author: jiangtao.liang
 * date:   On 2019-10-30 19:44
 */

@BindingAdapter("bind:imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    ImageLoader.load(imageView.context, url, R.drawable.placeholder_image, imageView)
}



