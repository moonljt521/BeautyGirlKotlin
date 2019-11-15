package com.moon.beautygirlkotlin.wei1.viewmodel

import androidx.lifecycle.MutableLiveData
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.wei1.repository.OnlyOneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * author: jiangtao.liang
 * date:   On 2019-10-31 12:35
 * des : 唯一图库 viewModel
 */
class OnlyOneViewModel(private val repository: OnlyOneRepository) : BaseViewModel() {

    private val url: String = "http://www.umei.cc/bizhitupian/meinvbizhi/"

    val list = ArrayList<MeiZiTuBody>()

    val _item: MutableLiveData<List<MeiZiTuBody>> by lazy {
        MutableLiveData<List<MeiZiTuBody>>()
    }

    fun getList(type: String, pageNum: Int) {
        launch({
            if (pageNum == 1) {
                list.clear()
            }

            val result = repository.getOnlyOneList(type, pageNum)

            list.addAll(result)

            _item.value = result
        },
                {

                }
        )
    }


    val youtuList = ArrayList<MeiZiTuBody>()

    val youtuData : MutableLiveData<List<MeiZiTuBody>> by lazy {
        MutableLiveData<List<MeiZiTuBody>>()
    }

    fun getTaoFemaleList(pageNum: Int) {
        launch({
            try {
                if (pageNum == 1) {
                    youtuList.clear()
                }
                val result = getData(pageNum)
                youtuList.addAll(result)
                youtuData.value = result
            } catch (e: Exception) {
                e.printStackTrace()

            }
        }, {

        })
    }

    private suspend fun getData(pageNum: Int): List<MeiZiTuBody> {
        return withContext(Dispatchers.IO) {
            if (pageNum == 1) {
                DataUtil.parserMeiTuLuHtml(url)
            } else {
                DataUtil.parserMeiTuLuHtml(url + pageNum + ".htm")
            }
        }
    }
}