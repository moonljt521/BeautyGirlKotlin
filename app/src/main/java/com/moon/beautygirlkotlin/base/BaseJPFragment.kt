package com.moon.beautygirlkotlin.base

import android.os.Bundle
import android.view.View

/**
 * author: moon
 * created on: 2017/5/17 下午8:09
 * description:  fragment 基类
 */
abstract class BaseJPFragment : AbstractFragment(){

    protected abstract fun initData()

    protected abstract fun initViews(view: View?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

}
