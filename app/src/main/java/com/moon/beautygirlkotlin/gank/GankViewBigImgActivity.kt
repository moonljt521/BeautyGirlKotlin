package com.moon.beautygirlkotlin.gank

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_gank_view_bigimg.*

/**
 * author: moon
 * created on: 18/4/28 上午11:43
 * description: 大图片浏览页面
 */
class GankViewBigImgActivity: AppCompatActivity() ,View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gank_view_bigimg)

        val img = intent?.getStringExtra("url")


        ImageLoader.load(this,img!!,gank_big_img)

        gank_big_img.enable()

        gank_big_img.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        finish()
    }



}