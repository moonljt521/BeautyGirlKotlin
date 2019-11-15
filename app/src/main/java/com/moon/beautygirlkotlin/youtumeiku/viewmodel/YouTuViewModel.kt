package com.moon.beautygirlkotlin.youtumeiku.viewmodel

import androidx.lifecycle.MutableLiveData
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.youtumeiku.repository.YouTuRepository

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 12:35
 * des : 优图美库 viewModel
 */
class YouTuViewModel(private val repository: YouTuRepository) : BaseViewModel<MeiZiTuBody>() {

    val youtuData: MutableLiveData<List<MeiZiTuBody>> by lazy {
        MutableLiveData<List<MeiZiTuBody>>()
    }

    fun getYouTuList(pageNum: Int) {
        launch({
            try {
                if (pageNum == 1) {
                    list.clear()
                }

                val result = repository.getYoutuRepository(pageNum)
                list.addAll(result)
                youtuData.value = result
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }, {
            it.printStackTrace()
        })
    }
}