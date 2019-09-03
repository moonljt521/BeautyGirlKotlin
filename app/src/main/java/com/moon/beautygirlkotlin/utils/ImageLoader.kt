package com.moon.beautygirlkotlin.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.moon.beautygirlkotlin.glide.GlideCircleTransForm

/**
 * author: moon
 * created on: 18/4/28 上午11:16
 * description:
 */
class ImageLoader {

    companion object {

        fun load(context : Context, url: String , view: ImageView){

            Glide.with(context).load(url).into(view)

        }

        fun load(context : Context, url: String ,placeholder: Int, view: ImageView){

            Glide.with(context).load(url).placeholder(placeholder).into(view)
        }


        fun loadCircle(context : Context, url: String , view: ImageView){

            Glide.with(context).load(url).transform(GlideCircleTransForm(context)).into(view)
        }


        fun loadCircle(context : Context, url: Int , view: ImageView){

            Glide.with(context).load(url).transform(GlideCircleTransForm(context)).into(view)
        }


        fun load(context : Context, id: Int , view: ImageView){

            Glide.with(context).load(id).into(view)

        }


        fun loadCircle(context : Context, id: Int , placeholder: Int,view: ImageView){

            Glide.with(context).load(id).placeholder(placeholder).transform(GlideCircleTransForm(context)).into(view)

        }

        fun loadCircle(context : Context, imageUrl: String , placeholder: Int,view: ImageView){

            Glide.with(context).load(imageUrl).placeholder(placeholder).transform(GlideCircleTransForm(context)).into(view)

        }

    }


}