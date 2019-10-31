package com.moon.beautygirlkotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.databinding.FragmentBaseRequestListBinding
import com.moon.beautygirlkotlin.databinding.FragmentJpGankMeiziBinding

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 19:47
 */
class BaseRequestListFragment : BaseJPFragment(){

    private val viewModel by  lazy {
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initViews(view: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onDestroy() {
        super.onDestroy()
    }

}