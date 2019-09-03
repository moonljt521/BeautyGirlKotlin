package com.moon.beautygirlkotlin.network


open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> T): T {
        return call.invoke()
    }

}