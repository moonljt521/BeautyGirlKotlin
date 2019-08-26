package com.moon.beautygirlkotlin.taofemale.presenter

import com.moon.beautygirlkotlin.taofemale.view.ITaoFemaleView
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.utils.presenterScope
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.mvpframework.presenter.BaseMvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaoFemalePresenter : BaseMvpPresenter<ITaoFemaleView>() {

    fun getTaoFemaleList(pageNum: Int) {
//        executeRequest<List<MeiZiTuBody>>(
//                context,
//                request = {
//                    var url: String? = null
//                    if (pageNum == 1) {
//                        url = "https://www.7160.com/xiaohua/"
//                    }else{
//                        url = "https://www.7160.com/xiaohua/list_6_" + pageNum + ".html"
//                    }
//                    DataUtil.parserMeiTuLuHtml(url)
//                },
//
//                onSuccess = {
//                    mvpView?.showSuccess(it)
//                },
//
//                onFail = {
//                    mvpView.showError()
//                }
//        )

        presenterScope.launch {
            try {
                val data = getData(pageNum)
                mvpView?.showSuccess(data)
            }catch (e : Exception){
                e.printStackTrace()
                mvpView?.showError()
            }
        }
    }

    suspend fun getData(pageNum: Int) : List<MeiZiTuBody>{
        return withContext(Dispatchers.IO){
            var url: String? = null
            if (pageNum == 1) {
                url = "https://www.7160.com/xiaohua/"
            }else{
                url = "https://www.7160.com/xiaohua/list_6_" + pageNum + ".html"
            }
            DataUtil.parserMeiTuLuHtml(url)
        }
    }
}