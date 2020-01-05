package com.moon.beautygirlkotlin.girl.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.beautygirlkotlin.common.data.entity.Source

/**
 * Created by Arthur on 2019-12-29.
 */
class GirlMainViewModel(source: Source) : ViewModel() {

    val isTabShowing = MutableLiveData<Boolean>(!source.types.isNullOrEmpty())


}