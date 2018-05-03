package com.moon.beautygirlkotlin.meizitu.presenter

import com.moon.beautygirlkotlin.doubanmeizi.view.IDouBanView
import com.moon.beautygirlkotlin.meizitu.view.IMeiZiTuView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.lang.Exception

/**
 *  妹子图  api 接口
 */
class MeiZiTuPresenter : BaseMvpPresenter<IMeiZiTuView>() {

    fun getMeizitu(context: RxAppCompatActivity, type: String, pageNum: Int) {

        RetrofitHelper.getMeiziTuApi()
                .getMeiziTuApi(type, pageNum)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .map{
                    resp ->
                    DataUtil.parserMeiziTuHtml(resp.toString(),type)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    list -> mvpView?.showSuccess(list)

                }, {

                    throwable ->

                    try {
                        mvpView?.showError()

                    }catch (e : Exception){
                        e.printStackTrace()
                    }


                })

    }


}