package com.moon.beautygirlkotlin.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * author: moon
 * created on: 2017/5/17 下午8:09
 * description:  fragment mvp 基类
 */

abstract class BaseFragment : Fragment() {

    protected abstract fun initData()

    protected abstract fun initViews(view: View?)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }
}
