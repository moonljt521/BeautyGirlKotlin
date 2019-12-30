package com.moon.beautygirlkotlin.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moon.beautygirlkotlin.data.entity.Source
import com.moon.beautygirlkotlin.data.entity.SourceType
import com.moon.beautygirlkotlin.data.service.DataServiceContext2
import com.moon.beautygirlkotlin.data.test.GirlRepository
import com.moon.beautygirlkotlin.test.paging.GirlListViewModel2

class GirlListViewModelFactory(private val source: Source,private  val sourceTye: SourceType?) :
        ViewModelProvider
.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                val serviceContext2 = DataServiceContext2(source)
//
                val rep = GirlRepository(serviceContext2)
                when {

                    isAssignableFrom(GirlListViewModel::class.java) -> {

                        GirlListViewModel(rep, sourceTye)

                    }
                    isAssignableFrom(GirlListViewModel2::class.java) -> {

                        GirlListViewModel2(rep, sourceTye)


                    }


                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T


}