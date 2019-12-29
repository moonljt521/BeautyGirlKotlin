package com.moon.beautygirlkotlin.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.beautygirlkotlin.data.entity.Source

/**
 * Created by Arthur on 2019-12-29.
 */
class GirlMainViewModel(source: Source) : ViewModel() {

    val isTabShowing = MutableLiveData<Boolean>(!source.types.isNullOrEmpty())


}