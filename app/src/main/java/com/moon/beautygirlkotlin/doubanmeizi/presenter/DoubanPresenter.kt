package com.moon.beautygirlkotlin.doubanmeizi.presenter

import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.doubanmeizi.view.IDouBanView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.utils.presenterScope
import com.moon.mvpframework.presenter.BaseMvpPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * 豆瓣妹子api 接口
 */
class DoubanPresenter : BaseMvpPresenter<IDouBanView>() {

    fun getDouBanMeiZiData(cid: Int, page: Int, type: Int) :Job{

//       return executeRequest<List<DoubanMeiziBody>>(
//                context,
//                request = {
//                    val response : Response<ResponseBody> = RetrofitHelper.getDoubanMeiziApi().getDoubanMeizi(cid, page)
//                    DataUtil.getDouBanList(type,response)
//                },
//
//                onSuccess = {
//                    if (it.size <= 0) {
//                        mvpView?.showError()
//                    }else{
//                        mvpView?.showSuccess(it)
//                    }
//                },
//
//                onFail = {
//                    mvpView?.showError()
//                }
//        )
       return presenterScope.launch {
            try {
                val data = getData(cid,page,type)
                mvpView?.showSuccess(data)
            }catch (e : Exception){
                e.printStackTrace()
                mvpView?.showError()
            }
        }
    }

    suspend fun getData(cid: Int, page: Int , type: Int): List<DoubanMeiziBody> {
        return withContext(Dispatchers.IO){
            val result: Response<ResponseBody> = RetrofitHelper.getDoubanMeiziApi().getDoubanMeizi(cid, page)
            DataUtil.getDouBanList(type,result)
        }
    }
}