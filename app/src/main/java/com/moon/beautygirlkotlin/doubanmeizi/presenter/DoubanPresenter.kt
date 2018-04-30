package com.moon.beautygirlkotlin.doubanmeizi.presenter

import android.content.Context
import com.moon.beautygirlkotlin.doubanmeizi.view.IDouBanView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.lang.Exception


/**
 * 豆瓣妹子api 接口
 */
class DoubanPresenter : BaseMvpPresenter<IDouBanView>() {

    fun getDouBanMeiZiData(context: RxAppCompatActivity, cid: Int, page: Int, type: Int) {

        RetrofitHelper.getDoubanMeiziApi()
                .getDoubanMeizi(cid, page)
                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
                .map{
                    resp -> DataUtil.getDouBanList(type, resp)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    list -> mvpView?.showSuccess(list)

                }, {
                    try {
                        mvpView?.showError()

                    }catch (e : Exception){
                        e.printStackTrace()
                    }
//                    throwable ->


                })

    }


}