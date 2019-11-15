package com.moon.beautygirlkotlin.youtumeiku.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moon.beautygirlkotlin.youtumeiku.viewmodel.YouTuViewModel

class YouTuModelFactory(private val repository: YouTuRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return YouTuViewModel(repository) as T
    }
}