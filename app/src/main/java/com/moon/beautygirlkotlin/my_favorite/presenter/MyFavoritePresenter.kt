package com.moon.beautygirlkotlin.my_favorite.presenter

import androidx.appcompat.app.AppCompatActivity
import com.moon.beautygirlkotlin.my_favorite.view.IMyFavoriteView
import com.moon.beautygirlkotlin.realm.RealmUtil
import com.moon.mvpframework.presenter.BaseMvpPresenter


/**
 * 图片收藏 p
 */
class MyFavoritePresenter : BaseMvpPresenter<IMyFavoriteView>() {

    fun getMyCollectList(context: AppCompatActivity) {

        try {
            mvpView?.showSuccess(RealmUtil.getCollectAll())

        }catch (e: Exception){
            e.printStackTrace()

            mvpView?.showError()
        }

//        Observable.create(Observable.OnSubscribe<List<MyCollectBody>>
//        {
//            sub ->
//
//            sub?.onNext(RealmUtil.getCollectAll())
//
//        }).compose(context.bindUntilEvent(ActivityEvent.DESTROY))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//
//                        {
//                            list -> mvpView?.showSuccess(list)
//                        },
//                        {
//                            e -> mvpView.showError()
//                            e.printStackTrace()
//                        }
//                )

    }
}