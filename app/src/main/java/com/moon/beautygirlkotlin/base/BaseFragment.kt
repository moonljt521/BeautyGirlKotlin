package com.moon.beautygirlkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * author: moon
 * created on: 2017/5/17 下午8:09
 * description:  fragment mvp 基类
 */

abstract class BaseFragment : Fragment() {

    protected lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = (context as AppCompatActivity?)!!
    }

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

    override fun onDetach() {
        super.onDetach()
    }
}
