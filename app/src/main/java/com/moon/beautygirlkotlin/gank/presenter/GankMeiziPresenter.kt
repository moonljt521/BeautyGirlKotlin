package com.moon.beautygirlkotlin.gank.presenter

import android.content.Context
import android.util.Log
import com.moon.beautygirlkotlin.gank.model.GankMeiziResult
import com.moon.beautygirlkotlin.gank.view.IGankMeiziView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class GankMeiziPresenter : BaseMvpPresenter<IGankMeiziView>() {

    fun getGankList(context: Context, pageNum: Int , page :Int){

        RetrofitHelper.getGankMeiziApi()
                .getGankMeizi(pageNum, page)
//                .compose((context).<GankMeiziResult>bindUntilEvent(ActivityEvent.DESTROY))
                .filter({ gankMeiziResult -> !gankMeiziResult.error })
                .map({ gankMeiziResult -> gankMeiziResult.results })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ gankMeiziInfos ->

                    mvpView?.showSuccess(gankMeiziInfos)

                }, { throwable ->

                    mvpView?.showError()

                })

    }

}