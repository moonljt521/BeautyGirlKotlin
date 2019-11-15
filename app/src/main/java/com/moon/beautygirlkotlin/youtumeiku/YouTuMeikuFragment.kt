package com.moon.beautygirlkotlin.youtumeiku

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseRequestListFragment
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.utils.InjectorUtil
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.youtumeiku.viewmodel.YouTuViewModel

/**
 * [优图美库] 模块 fragment
 */
class YouTuMeikuFragment : BaseRequestListFragment<MeiZiTuBody>() {

    private val viewModel by lazy { ViewModelProviders.of(this, InjectorUtil.getYouTuModelFactory()).get(YouTuViewModel::class.java) }

    override fun loadData() {
        viewModel.getYouTuList(page)
        viewModel.youtuData.observe(this, this)
    }

    override fun getViewModel(): BaseViewModel<MeiZiTuBody> = viewModel

    override fun getItemLayoutId(): Int = R.layout.item_only_one

    companion object {
        fun getInstance(id: Int): YouTuMeikuFragment {
            val fragment = YouTuMeikuFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onClick(view: View, body: MeiZiTuBody) {
        ViewBigImgActivity.startViewBigImaActivity(mActivity, body.url,
                body.title, true)
    }
}