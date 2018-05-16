package com.moon.beautygirlkotlin.my_collect.presenter

import com.moon.beautygirlkotlin.my_collect.model.MyCollectBody
import com.moon.beautygirlkotlin.my_collect.view.IMyCollectView
import com.moon.beautygirlkotlin.realm.RealmUtil
import com.moon.beautygirlkotlin.utils.Logger
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import java.lang.Exception

class MyCollectPresenter : BaseMvpPresenter<IMyCollectView>() {

    fun getMyCollectList(context: RxAppCompatActivity) {

        try {
            val list = RealmUtil.getRealm().where(MyCollectBody::class.java).findAll()

            Logger.i("size = " + list?.size)

            if (mvpView == null){
                Logger.i("mvpView == null")
            }

            mvpView?.showSuccess(list)

            for (i: MyCollectBody in list) {
                Logger.i("url = " + i.url + ",title = " + i.title)
            }
        }catch (e: Exception){
            e.printStackTrace()

            mvpView?.showError()

        }




    }

}