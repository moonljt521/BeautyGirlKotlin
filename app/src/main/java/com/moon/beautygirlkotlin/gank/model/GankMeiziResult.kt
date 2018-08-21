package com.moon.beautygirlkotlin.gank.model

import com.moon.beautygirlkotlin.base.BaseBean

data class GankMeiziResult(var error: Boolean
                        , var results : List<GankMeiziBody>) : BaseBean() {
}