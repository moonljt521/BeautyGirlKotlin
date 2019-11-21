package com.moon.beautygirlkotlin.gank

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseRequestListFragment
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.beautygirlkotlin.gank.viewmodel.GankViewModel
import com.moon.beautygirlkotlin.utils.InjectorUtil
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity

/**
 * author: jiangtao.liang
 * date:   On 2019-11-15 14:28
 */
class GankFragment : BaseRequestListFragment<GankMeiziBody>() {

    private val viewModel by lazy { ViewModelProviders.of(this, InjectorUtil.getGankModelFactory()).get(GankViewModel::class.java) }

    override fun getViewModel(): BaseViewModel<GankMeiziBody> = viewModel

    companion object {
        fun getInstance(id: Int): GankFragment {
            val fragment = GankFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter?.ontItemClick = viewModel
        viewModel.itemData.observe(this, Observer {
            ViewBigImgActivity.startViewBigImaActivity(mActivity, it.url,
                    it.desc, true)
        })
    }

    override fun loadData() {
        viewModel.getGankList(pageSize, page)
    }

    override fun getItemLayoutId(): Int = R.layout.item_meng_meizi

}