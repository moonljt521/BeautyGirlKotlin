package com.moon.beautygirlkotlin.girl.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moon.beautygirlkotlin.common.data.entity.Source

/**
 * Created by Arthur on 2019-12-29.
 */
class GirlMainViewModelFactory(private val source: Source) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return GirlMainViewModel(source) as T
    }
}