package com.moon.beautygirlkotlin.doubanmeizi

import android.os.Bundle
import androidx.lifecycle.Observer
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

    override fun loadData() {
        mAdapter?.ontItemClick = viewModel
        viewModel.getList(arguments!!.getInt("id"), page, 1)
        viewModel.itemData.observe(this, Observer {
            ViewBigImgActivity.startViewBigImaActivity(requireContext(), it.url,
                    it.title, true)
        })
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