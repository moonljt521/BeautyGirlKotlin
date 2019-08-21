package com.moon.beautygirlkotlin.base

import com.trello.rxlifecycle.components.support.RxFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * author: jiangtao.liang
 * date:   On 2019/8/21 13:18
 */
@ExperimentalCoroutinesApi
abstract class AbstractFragment : RxFragment() , CoroutineScope by MainScope() {

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}