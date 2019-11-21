package com.moon.beautygirlkotlin.wei1

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseRequestListFragment
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.utils.InjectorUtil
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.wei1.viewmodel.OnlyOneViewModel

/**
 * 唯一图库 模块 子fragment
 */
class OnlyOneSimpleFragment : BaseRequestListFragment<MeiZiTuBody>() {

    lateinit var type: String

    val viewModel: OnlyOneViewModel by lazy {
        ViewModelProviders.of(this, InjectorUtil.getOnlyOneModelFactory()).get(OnlyOneViewModel::class.java)
    }

    override fun getViewModel(): BaseViewModel<MeiZiTuBody> = viewModel

    override fun getItemLayoutId(): Int = R.layout.item_only_one

    override fun loadData() {
        mAdapter?.ontItemClick = viewModel
        viewModel.getList(type, page)
        viewModel.itemData.observe(this, Observer {
            ViewBigImgActivity.startViewBigImaActivity(mActivity, it.content.url,
                    it.content.title, true)
        })
    }

    companion object {
        fun getInstance(cid: String): OnlyOneSimpleFragment {
            val fragment = OnlyOneSimpleFragment();
            val bundle = Bundle()
            bundle.putString("type", cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        type = arguments?.getString("type")!!
    }
}