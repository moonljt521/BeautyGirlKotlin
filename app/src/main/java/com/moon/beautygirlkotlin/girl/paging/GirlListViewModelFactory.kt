package com.moon.beautygirlkotlin.girl.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moon.beautygirlkotlin.common.data.entity.Source
import com.moon.beautygirlkotlin.common.data.entity.SourceType
import com.moon.beautygirlkotlin.common.data.repository.GirlRepository
import com.moon.beautygirlkotlin.common.data.service.DataServiceContext

class GirlListViewModelFactory(private val source: Source,private  val sourceTye: SourceType?) :
        ViewModelProvider
.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                val serviceContext2 = DataServiceContext(source)
//
                val rep = GirlRepository(serviceContext2)
                when {


                    isAssignableFrom(GirlListViewModel::class.java) -> {

                        GirlListViewModel(rep, sourceTye)


                    }


                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T


}