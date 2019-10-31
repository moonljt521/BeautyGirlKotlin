package com.moon.beautygirlkotlin.wei1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moon.beautygirlkotlin.wei1.repository.OnlyOneRepository
import com.moon.beautygirlkotlin.wei1.viewmodel.OnlyOneViewModel

class OnlyOneModelFactory(private val repository: OnlyOneRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OnlyOneViewModel(repository) as T
    }
}