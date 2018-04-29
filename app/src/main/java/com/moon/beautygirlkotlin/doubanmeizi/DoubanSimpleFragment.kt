package com.moon.beautygirlkotlin.doubanmeizi

import android.os.Bundle
import android.util.Log
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.doubanmeizi.presenter.DoubanPresenter
import com.moon.beautygirlkotlin.doubanmeizi.view.IDouBanView
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.mvpframework.view.BaseLazeFragment

/**
 * 豆瓣模块 子fragment
 */
class DoubanSimpleFragment : BaseLazeFragment<IDouBanView,DoubanPresenter>(),IDouBanView {

    private val pageNum = 20

    private var page = 1


    // 加载数据
    override fun loadData() {


        loadFinish = true
    }


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
    override fun init() {


    }

    override fun initViews(view: View?) {



    }

    override fun showError() {


    }

    override fun showSuccess(t: List<DoubanMeiziBody>?) {

    }



}