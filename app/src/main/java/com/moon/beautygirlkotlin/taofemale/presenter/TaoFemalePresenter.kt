package com.moon.beautygirlkotlin.taofemale.presenter

import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.taofemale.view.ITaoFemaleView
import com.moon.beautygirlkotlin.utils.ConstantUtil
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class TaoFemalePresenter : BaseMvpPresenter<ITaoFemaleView>() {

    fun getGankList(context: RxAppCompatActivity, pageNum: Int){

        RetrofitHelper.getTaoFemaleApi()
                .getTaoFemale(pageNum, ConstantUtil.APP_ID, ConstantUtil.APP_SIGN)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .map({ taofemale -> taofemale.showapi_res_body.pagebean.contentlist })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->

                    mvpView?.showSuccess(list)

                }, { throwable ->

                    mvpView?.showError()

                })

    }

}