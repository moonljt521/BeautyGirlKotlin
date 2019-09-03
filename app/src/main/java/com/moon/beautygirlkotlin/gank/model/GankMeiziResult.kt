package com.moon.beautygirlkotlin.gank.model

import com.moon.beautygirlkotlin.base.BaseResponse

data class GankMeiziResult(var results : List<GankMeiziBody>) : BaseResponse<List<GankMeiziBody>>() {
}