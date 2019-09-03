package com.moon.beautygirlkotlin.huaban

import android.os.Bundle
import android.support.design.widget.TabLayout.MODE_SCROLLABLE
import android.util.TypedValue
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.huaban.model.HuaBanFragmentAdapter
import kotlinx.android.synthetic.main.fragment_base_douban_meizi.*

/**
 * [花瓣] 模块 base  fragment
 */
class HuaBanBaseFragment : BaseFragment<Nothing, Nothing>()
{
    lateinit var adapter: HuaBanFragmentAdapter

    companion object {

        fun getInstance(id: Int): HuaBanBaseFragment {
            var fragment = HuaBanBaseFragment();
            var bundle = Bundle()
            bundle.putInt("id", id)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun getLayoutId(): Int {

        return R.layout.fragment_base_douban_meizi
    }

    /**
     * 初始化
     */
    override fun initData() {

        val pageMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources
                .displayMetrics).toInt()

        douban_viewpager.offscreenPageLimit = 6

        douban_viewpager.setPageMargin(pageMargin)
        adapter = HuaBanFragmentAdapter(childFragmentManager)
        douban_viewpager.adapter = (adapter)
        douban_tablayout.tabMode = MODE_SCROLLABLE
        douban_tablayout.setupWithViewPager(douban_viewpager)
    }

    override fun initViews(view: View?) {

    }
}