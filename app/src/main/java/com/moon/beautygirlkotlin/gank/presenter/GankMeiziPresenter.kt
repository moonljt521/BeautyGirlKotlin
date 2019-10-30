package com.moon.beautygirlkotlin.gank.presenter

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
                val data = withContext(Dispatchers.IO){
                    RetrofitHelper.getGankMeiziApi().getGankMeizi(pageNum, page)
                }
                mvpView?.showSuccess(data.results)
            }catch (e : Exception){
                e.printStackTrace()
                mvpView.showError()
            }
        }
    }
}