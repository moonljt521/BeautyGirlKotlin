package com.moon.beautygirlkotlin.base

import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

/**
 * author: jiangtao.liang
 * date:   On 2019/8/21 13:18
 */
abstract class AbstractFragment : Fragment() , CoroutineScope by MainScope() {

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}