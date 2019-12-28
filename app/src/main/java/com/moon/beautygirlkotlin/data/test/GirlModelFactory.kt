package com.moon.beautygirlkotlin.data.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moon.beautygirlkotlin.data.entity.Source
import com.moon.beautygirlkotlin.data.service.DataServiceContext2

class GirlModelFactory(private val source: Source) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val serviceContext2 = DataServiceContext2(source)

        val rep = GirlRepository(serviceContext2)

        return GirlViewModel(rep) as T
    }
}