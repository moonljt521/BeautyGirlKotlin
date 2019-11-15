package com.moon.beautygirlkotlin.doubanmeizi

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseRequestListFragment
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.doubanmeizi.viewmodel.DoubanViewModel
import com.moon.beautygirlkotlin.utils.InjectorUtil
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity

/**
 * 豆瓣模块 子fragment
 */
class DoubanSimpleFragment : BaseRequestListFragment<DoubanMeiziBody>() {

    val viewModel by lazy {
        ViewModelProviders.of(this, InjectorUtil.getDoubanModelFactory()).get(DoubanViewModel::class.java)
    }

    override fun getViewModel(): BaseViewModel<DoubanMeiziBody> = viewModel

    override fun getItemLayoutId(): Int = R.layout.item_douban

    override fun onClick(view: View, body: DoubanMeiziBody) {
        ViewBigImgActivity.startViewBigImaActivity(mActivity, body.url,
                body.title, true)
    }

    override fun loadData() {
        viewModel.getList(arguments!!.getInt("id"), page, 1)
        viewModel.data.observe(viewLifecycleOwner, this)
    }

    companion object {
        fun getInstance(id: Int): DoubanSimpleFragment {
            val fragment = DoubanSimpleFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }
}