package com.moon.beautygirlkotlin.gank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GankModelFactory(private val repository: GankRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GankViewModel(repository) as T
    }
}