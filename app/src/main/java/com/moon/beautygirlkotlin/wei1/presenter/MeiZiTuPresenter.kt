package com.moon.beautygirlkotlin.wei1.presenter

import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.utils.presenterScope
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.wei1.view.IMeiZiTuView
import com.moon.mvpframework.presenter.BaseMvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *  妹子图  api 接口
 */
class MeiZiTuPresenter : BaseMvpPresenter<IMeiZiTuView>() {

    fun getMeizitu(type: String, pageNum: Int) {
//        executeRequest<List<MeiZiTuBody>>(
//                context,
//                request = {
//                    DataUtil.parserMeiziTuHtml(
//                            RetrofitHelper.BASE_MEIZITU_URL + type + pageNum + ".html"
//                    )
//                },
//
//                onSuccess = {
//                    mvpView?.showSuccess(it)
//                },
//
//                onFail = {
//                    mvpView?.showError()
//                }
//        )

        presenterScope.launch {
            try {
                val data = getData(type,pageNum)
                mvpView?.showSuccess(data)
            }catch (e : Exception){
                e.printStackTrace()
                mvpView?.showError()
            }
        }
    }

    suspend fun getData(type: String, pageNum: Int) : List<MeiZiTuBody>{
        return withContext(Dispatchers.IO){
            DataUtil.parserMeiziTuHtml(
                    RetrofitHelper.BASE_MEIZITU_URL + type + pageNum + ".html")
        }
    }
}