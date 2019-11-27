package com.moon.beautygirlkotlin.wei1.viewmodel

import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.wei1.repository.OnlyOneRepository

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 12:35
 * des : 唯一图库 viewModel
 */
class OnlyOneViewModel(private val repository: OnlyOneRepository) : BaseViewModel<MeiZiTuBody>() {

    fun getList(type: String, pageNum: Int) {
        launch({
            if (pageNum == 1) {
                list.clear()
            }

            val result = repository.getOnlyOneList(type, pageNum)

            list.addAll(result)

            data.value = result
        })
    }

}