package com.moon.beautygirlkotlin.gank.presenter

import com.moon.beautygirlkotlin.gank.view.IGankMeiziView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.Logger
import com.moon.beautygirlkotlin.utils.executeResponse
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GankMeiziPresenter : BaseMvpPresenter<IGankMeiziView>() {

    fun getGankList(context: RxAppCompatActivity, pageNum: Int , page :Int){

//        RetrofitHelper.getGankMeiziApi()
//                .getGankMeizi(pageNum, page)
//                .subscribeOn(Schedulers.io())
//                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
//                .filter({ gankMeiziResult -> !gankMeiziResult.error })
//                .map({ gankMeiziResult -> gankMeiziResult.results })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ gankMeiziInfos ->
//
//                    mvpView?.showSuccess(gankMeiziInfos)
//
//                }, { throwable ->
//
//                    mvpView?.showError()
//
//                })


        GlobalScope.launch(Dispatchers.IO){

            try {
                val response =  RetrofitHelper.getGankMeiziApi().getGankMeizi(pageNum ,page);

                withContext(Dispatchers.Main){

                    if (response?.results?.size > 0){
                        mvpView.showSuccess(response?.results)
                    }else {
                        mvpView.showError()
                    }
                }
            }catch (e :Exception){
                e.printStackTrace()
                mvpView.showError()
            }

        }
    }

}