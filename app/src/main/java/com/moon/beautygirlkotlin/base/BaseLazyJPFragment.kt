package com.moon.beautygirlkotlin.base

import android.os.Bundle
import android.view.View

/**
 * author: moon
 * created on: 2017/5/17 下午8:09
 * description:  fragment 基类
 */
abstract class BaseLazyJPFragment : AbstractFragment(){

    // 懒加载
    protected var fragmentIsVisible: Boolean = false

    protected var isPrepared: Boolean = false

    protected var loadFinish: Boolean = false

    protected abstract fun initData()

    protected abstract fun initViews(view: View?)

    protected abstract fun loadData()

    protected fun onInvisible() {}

    protected fun onVisible() {
        lazyLoad()
    }

    private fun lazyLoad() {
        if (!isPrepared || !fragmentIsVisible || loadFinish) {
            return
        }
        loadData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        isPrepared = true
        lazyLoad()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    // 懒加载 方法
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (userVisibleHint) {
            fragmentIsVisible = true
            onVisible()
        } else {
            fragmentIsVisible = false
            onInvisible()
        }
    }

}
