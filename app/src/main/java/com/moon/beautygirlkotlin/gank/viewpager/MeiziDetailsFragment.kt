package com.moon.beautygirlkotlin.gank.viewpager

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.doubanmeizi.DoubanSimpleFragment
import com.moon.mvpframework.view.BaseLazeFragment
import kotlinx.android.synthetic.main.fragment_meizi_details.*
import uk.co.senab.photoview.PhotoViewAttacher
import java.lang.Exception

/**
 * author: moon
 * created on: 18/4/25 下午5:34
 * description:
 */
class MeiziDetailsFragment : BaseLazeFragment<Nothing,Nothing>() , RequestListener<String,GlideDrawable> {


    private lateinit var url : String

    private var mPhotoViewAttacher: PhotoViewAttacher? = null

    companion object {

        private val EXTRA_URL = "extra_url"

        fun newInstance(url: String): DoubanSimpleFragment {
            var fragment = DoubanSimpleFragment();
            var bundle = Bundle()
            bundle.putString(EXTRA_URL, url)

            fragment.arguments = bundle

            return fragment
        }
    }


    override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {

        tv_image_error.visibility = View.VISIBLE
        return false
    }

    override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {

        meizi.setImageDrawable(resource)
        mPhotoViewAttacher = PhotoViewAttacher(meizi)
        tv_image_error.setVisibility(View.GONE)
        setPhotoViewAttacher()

        return false
    }

    private fun setPhotoViewAttacher() {
       // 保存图片
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_meizi_details
    }

    override fun init() {

    }

    override fun initViews(view: View?) {
        url = arguments.getString(EXTRA_URL)
        Glide.with(this).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade(0)
                .listener(this)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
    }

    override fun loadData() {

    }

    fun getSharedElement(): View {

        return meizi
    }

}