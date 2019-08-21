package com.moon.beautygirlkotlin.wei1.presenter

import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.wei1.view.IMeiZiTuView
import com.moon.beautygirlkotlin.network.RetrofitHelper
import com.moon.beautygirlkotlin.utils.DataUtil
import com.moon.beautygirlkotlin.utils.executeRequest
import com.moon.mvpframework.presenter.BaseMvpPresenter

/**
 *  妹子图  api 接口
 */
class MeiZiTuPresenter : BaseMvpPresenter<IMeiZiTuView>() {

    fun getMeizitu(type: String, pageNum: Int) {
        executeRequest<List<MeiZiTuBody>>(
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