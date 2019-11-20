package com.moon.beautygirlkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * author: moon
 * created on: 2017/5/17 下午8:09
 * description:  fragment mvp 基类
 */

abstract class BaseFragment : Fragment() , CoroutineScope by MainScope() {

    protected lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = (context as AppCompatActivity?)!!
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
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
}
