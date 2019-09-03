package com.moon.beautygirlkotlin.gank.presenter

import com.moon.beautygirlkotlin.gank.model.GankMeiziResult
import com.moon.beautygirlkotlin.gank.view.IGankMeiziView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.presenterScope
import com.moon.mvpframework.presenter.BaseMvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GankMeiziPresenter : BaseMvpPresenter<IGankMeiziView>() {

    fun getGankList(pageNum: Int, page: Int) {
        presenterScope.launch {
            try {
                val data = getData(pageNum,page)
                mvpView?.showSuccess(data.results)
            }catch (e : Exception){
                e.printStackTrace()
                mvpView.showError()
            }
        }

//       return executeRequest<GankMeiziResult>(
//               context,
//                request = {
//                    RetrofitHelper.getGankMeiziApi().getGankMeizi(pageNum, page)
//                },
//
//                onSuccess = {
//                    mvpView?.showSuccess(it.results)
//                },
//
//                onFail = {
//                    mvpView?.showError()
//                }
//        )
    }

    suspend fun getData(pageNum: Int, page: Int) : GankMeiziResult {
        return withContext(Dispatchers.IO){
            RetrofitHelper.getGankMeiziApi().getGankMeizi(pageNum, page)
        }
    }
}