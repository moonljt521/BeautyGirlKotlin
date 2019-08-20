package com.moon.beautygirlkotlin.gank.presenter

import com.moon.beautygirlkotlin.gank.model.GankMeiziResult
import com.moon.beautygirlkotlin.gank.view.IGankMeiziView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.executeRequest4Gank
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

class GankMeiziPresenter : BaseMvpPresenter<IGankMeiziView>() {

    fun getGankList(context: RxAppCompatActivity, pageNum: Int, page: Int) {

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


        executeRequest4Gank<GankMeiziResult>(
                request = {
                    RetrofitHelper.getGankMeiziApi().getGankMeizi(pageNum, page)
                },

                onSuccess = {
                    mvpView?.showSuccess(it?.results)
                },

                onFail = {
                    mvpView?.showError()
                }
        )
    }

}