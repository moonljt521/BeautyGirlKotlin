package com.moon.beautygirlkotlin.doubanmeizi.viewmodel

import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.doubanmeizi.rep.DoubanRepository

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 20:03
 */
class DoubanViewModel(private val repository: DoubanRepository) : BaseViewModel<DoubanMeiziBody>() {

    fun getList(cid: Int, page: Int, type: Int) {
        launch({
            val result = repository.getDouBanMeiZiData(cid, page, type)

            result.let {
                if (page == 1) {
                    list.clear()
                }

                list.addAll(result)
                data.value = result
            }

        }, {

        })

    }
}