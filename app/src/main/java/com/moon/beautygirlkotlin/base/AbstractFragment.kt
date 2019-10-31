package com.moon.beautygirlkotlin.base

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.moon.mvpframework.factory.PresenterMvpFactoryImpl
import com.moon.mvpframework.proxy.BaseMvpProxy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * author: jiangtao.liang
 * date:   On 2019/8/21 13:18
 */
abstract class AbstractFragment : Fragment() , CoroutineScope by MainScope() {

    protected lateinit var mActivity: AppCompatActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = (context as AppCompatActivity?)!!
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}