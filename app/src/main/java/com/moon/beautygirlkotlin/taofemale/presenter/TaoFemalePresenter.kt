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

    private val url : String = "http://www.umei.cc/bizhitupian/meinvbizhi/"

    fun getTaoFemaleList(pageNum: Int) {
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
            if (pageNum == 1) {
                DataUtil.parserMeiTuLuHtml(url)
            }else{
                DataUtil.parserMeiTuLuHtml(url + pageNum + ".htm")
            }
        }
    }
}