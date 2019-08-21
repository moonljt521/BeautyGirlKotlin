package com.moon.beautygirlkotlin.meizitu

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.meizitu.adapter.MeiZiTuFragmentAdapter
import kotlinx.android.synthetic.main.fragment_base_meizitu.*

/**
 * 妹子图 模块 base  fragment
 */
class MeiZiTuBaseFragment : BaseFragment<Nothing, Nothing>()
{

    lateinit var adapter: MeiZiTuFragmentAdapter

    companion object {

        fun getInstance(id: Int): MeiZiTuBaseFragment {
            var fragment = MeiZiTuBaseFragment();
            var bundle = Bundle()
            bundle.putInt("id", id)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_base_meizitu
    }

    /**
     * 初始化
     */
    override fun initData() {

        val pageMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources
                .displayMetrics).toInt()

        meizitu_viewpager.offscreenPageLimit = 6

        meizitu_viewpager.setPageMargin(pageMargin)
        adapter = MeiZiTuFragmentAdapter(childFragmentManager)
        meizitu_viewpager.adapter = (adapter)
        meizitu_tablayout.setupWithViewPager(meizitu_viewpager)

    }

    override fun initViews(view: View?) {

    }


}