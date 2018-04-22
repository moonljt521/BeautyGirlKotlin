package com.moon.beautygirlkotlin.doubanmeizi

import android.os.Bundle
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.mvpframework.view.BaseIdFragment

/**
 * 豆瓣模块 子fragment
 */
class DoubanSimpleFragment : BaseIdFragment<Nothing, Nothing>() {




    companion object {

        fun getInstance(id: Int): DoubanSimpleFragment {
            var fragment = DoubanSimpleFragment();
            var bundle = Bundle()
            bundle.putInt("id", id)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun getLayoutId(): Int {

        return R.layout.fragment_simple_douban_meizi
    }


    /**
     * 初始化
     */
    override fun initData() {


    }

    override fun initViews(view: View?) {



    }



}