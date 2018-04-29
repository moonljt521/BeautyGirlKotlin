package com.moon.beautygirlkotlin.doubanmeizi.presenter

import android.content.Context
import com.moon.beautygirlkotlin.doubanmeizi.view.IDouBanView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.mvpframework.presenter.BaseMvpPresenter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * 豆瓣妹子api 接口
 */
class DoubanPresenter : BaseMvpPresenter<IDouBanView>() {

    fun getDouBanMeiZiData(context: Context, cid: Int, page: Int, type: Int) {

        RetrofitHelper.getDoubanMeiziApi()
                .getDoubanMeizi(cid, page)
                .subscribeOn(Schedulers.io())
                .map{

                    resp -> DataUtil.getDouBanList(type, resp)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    list ->
                        mvpView?.showSuccess(list)

                }, { throwable ->

                    mvpView?.showError()

                })

    }


}