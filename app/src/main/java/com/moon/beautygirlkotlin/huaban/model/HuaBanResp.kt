package com.moon.beautygirlkotlin.huaban.model

import com.google.gson.JsonObject

/**
 * author: moon
 * created on: 18/5/3 下午12:58
 * description:
 */
data class HuaBanResp(var showapi_res_code : Int, var showapi_res_error : String , var showapi_res_body : JsonObject) {
}