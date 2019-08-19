package com.moon.beautygirlkotlin.utils

import com.moon.beautygirlkotlin.base.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

/**
 * author: jiangtao.liang
 * date:   On 2019/8/19 18:12
 * des : 扩展函数
 */


suspend fun executeResponse(response: BaseResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                            errorBlock: suspend CoroutineScope.() -> Unit) {
    coroutineScope {
        if (response.errorCode == -1) errorBlock()
        else successBlock()
    }
}
