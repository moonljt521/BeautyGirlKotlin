package com.moon.beautygirlkotlin.youtumeiku.viewmodel

import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.youtumeiku.repository.YouTuRepository

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 12:35
 * des : 优图美库 viewModel
 */
class YouTuViewModel(private val repository: YouTuRepository) : BaseViewModel<MeiZiTuBody>() {

    fun getYouTuList(pageNum: Int) {
        launch({
            if (pageNum == 1) {
                list.clear()
            }
            val result = repository.getYoutuRepository(pageNum)
            list.addAll(result)
            data.value = result
        })
    }
}