package com.moon.beautygirlkotlin.taofemale.model

import com.moon.beautygirlkotlin.base.BaseBean

data class TaoFemale(var showapi_res_body: ShowapiResBody,
                     var showapi_res_error: String
                     , var showapi_res_code: Int) : BaseBean(){
}