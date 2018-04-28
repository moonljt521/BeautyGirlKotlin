package com.moon.beautygirlkotlin.doubanmeizi

import android.os.Bundle
import android.util.Log
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.mvpframework.view.BaseLazeFragment

/**
 * 豆瓣模块 子fragment
 */
class DoubanSimpleFragment : BaseLazeFragment<Nothing, Nothing>() {


    // 加载数据
    override fun loadData() {
        Log.i("moon", javaClass.name + " id = " + arguments.getInt("id"))

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



}