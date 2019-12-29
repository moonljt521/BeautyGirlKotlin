package com.moon.beautygirlkotlin.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moon.beautygirlkotlin.data.entity.Source
import com.moon.beautygirlkotlin.data.entity.SourceType
import com.moon.beautygirlkotlin.data.service.DataServiceContext2
import com.moon.beautygirlkotlin.data.test.GirlRepository

class GirlListViewModelFactory(private val source: Source,private  val sourceTye: SourceType?) :
        ViewModelProvider
.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val serviceContext2 = DataServiceContext2(source)

        val rep = GirlRepository(serviceContext2)

        return GirlListViewModel(rep,sourceTye) as T
    }
}