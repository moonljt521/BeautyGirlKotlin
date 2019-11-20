package com.moon.beautygirlkotlin.model

import com.moon.beautygirlkotlin.base.MultiTypeAdapter

import java.util.Date
import java.util.Random

/**
 * author: jiangtao.liang
 * date:   On 2019-11-19 11:20
 */
open class BaseModel {

    /////////////////////////////////////////////////////////////////////
    val id: Int
    val updatedAt: Date

    open fun createItem(adapter: MultiTypeAdapter): MultiTypeAdapter.IItem? {
        return null
    }

    init {
        id = Random().nextInt(10000)
        updatedAt = Date()
    }
}
