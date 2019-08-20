package com.moon.beautygirlkotlin.doubanmeizi.presenter

import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.doubanmeizi.view.IDouBanView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.utils.executeRequest
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import okhttp3.ResponseBody
import retrofit2.Response


/**
 * 豆瓣妹子api 接口
 */
class DoubanPresenter : BaseMvpPresenter<IDouBanView>() {

    fun getDouBanMeiZiData(context: RxAppCompatActivity, cid: Int, page: Int, type: Int) {

//        executeRequest<Response<ResponseBody>>(
//
//                request = {
//                    RetrofitHelper.getDoubanMeiziApi().getDoubanMeizi(cid, page)
//                },
//
//                onSuccess = {
//                    val result: List<DoubanMeiziBody> = withContext(Dispatchers.IO) {
//                        DataUtil.getDouBanList(type, it)
//                    }
//
//                    withContext(Dispatchers.Main) {
//                        if (result.size <= 0) {
//                            mvpView.showError()
//                        }else{
//                            mvpView?.showSuccess(result)
//                        }
//                    }
//                },
//
//                onFail = {
//                    mvpView?.showError()
//                }
//        )

        executeRequest<List<DoubanMeiziBody>>(

                request = {
                    val response : Response<ResponseBody> = RetrofitHelper.getDoubanMeiziApi().getDoubanMeizi(cid, page)
                    DataUtil.getDouBanList(type,response)
                },

                onSuccess = {
                    if (it.size <= 0) {
                        mvpView.showError()
                    }else{
                        mvpView?.showSuccess(it)
                    }
                },

                onFail = {
                    mvpView?.showError()
                }
        )


//        RetrofitHelper.getDoubanMeiziApi()
//                .getDoubanMeizi(cid, page)
//                .compose(context.bindUntilEvent(ActivityEvent.DESTROY))
//                .map{
//                    resp -> DataUtil.getDouBanList(type, resp)
//                }
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//
//                    list -> mvpView?.showSuccess(list)
//
//                }, {
//
//                    throwable ->
//
//                    try {
//                        mvpView?.showError()
//
//                    }catch (e : Exception){
//                        e.printStackTrace()
//                    }
//
//
//                })

    }


}