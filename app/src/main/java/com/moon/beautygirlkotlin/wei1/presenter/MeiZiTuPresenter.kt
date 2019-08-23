package com.moon.beautygirlkotlin.wei1.presenter

import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.utils.executeRequest
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.wei1.view.IMeiZiTuView
import com.moon.mvpframework.presenter.BaseMvpPresenter
import kotlin.coroutines.CoroutineContext

/**
 *  妹子图  api 接口
 */
class MeiZiTuPresenter : BaseMvpPresenter<IMeiZiTuView>() {

    fun getMeizitu(context: CoroutineContext ,type: String, pageNum: Int) {
        executeRequest<List<MeiZiTuBody>>(
                context,
                request = {
                    DataUtil.parserMeiziTuHtml(
                            RetrofitHelper.BASE_MEIZITU_URL + type + pageNum + ".html"
                    )
                },

                onSuccess = {
                    mvpView?.showSuccess(it)
                },

                onFail = {
                    mvpView?.showError()
                }
        )
    }
}