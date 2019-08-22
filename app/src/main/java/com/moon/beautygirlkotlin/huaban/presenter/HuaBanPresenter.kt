package com.moon.beautygirlkotlin.huaban.presenter

import com.moon.beautygirlkotlin.huaban.view.IHuaBanView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.ConstantUtil
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 *  花瓣妹子api 接口
 */
class HuaBanPresenter : BaseMvpPresenter<IHuaBanView>() {

    fun getHuaBan(context: RxAppCompatActivity,
                  pageNum: Int,
                  page: Int,
                  cid: Int) {

        RetrofitHelper.getHuaBanMeiziApi()
                .getHuaBanMeizi(pageNum,
                        page ,
                        ConstantUtil.APP_ID ,
                        cid,
                        ConstantUtil.APP_SIGN)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .map{
                    resp ->
                    DataUtil.getHuaBanList(resp.string())
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