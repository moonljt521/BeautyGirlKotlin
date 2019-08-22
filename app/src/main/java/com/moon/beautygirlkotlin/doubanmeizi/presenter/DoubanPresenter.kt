package com.moon.beautygirlkotlin.doubanmeizi.presenter

import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.doubanmeizi.view.IDouBanView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.utils.executeRequest
import com.moon.mvpframework.presenter.BaseMvpPresenter
import kotlinx.coroutines.Job
import okhttp3.ResponseBody
import retrofit2.Response


/**
 * 豆瓣妹子api 接口
 */
class DoubanPresenter : BaseMvpPresenter<IDouBanView>() {

    fun getDouBanMeiZiData(cid: Int, page: Int, type: Int) :Job{

       return executeRequest<List<DoubanMeiziBody>>(

                request = {
                    val response : Response<ResponseBody> = RetrofitHelper.getDoubanMeiziApi().getDoubanMeizi(cid, page)
                    DataUtil.getDouBanList(type,response)
                },

                onSuccess = {
                    if (it.size <= 0) {
                        mvpView?.showError()
                    }else{
                        mvpView?.showSuccess(it)
                    }
                },

                onFail = {
                    mvpView?.showError()
                }
        )
    }
}