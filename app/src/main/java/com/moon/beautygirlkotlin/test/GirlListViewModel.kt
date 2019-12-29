package com.moon.beautygirlkotlin.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.entity.SourceType
import com.moon.beautygirlkotlin.data.test.GirlRepository
import kotlinx.coroutines.launch

class GirlListViewModel(val repository: GirlRepository,val sourceType: SourceType?) : ViewModel() {


    val itemData = MutableLiveData<List<GirlData>>()

    fun loaData(page: Int, pageNum: Int) {


        viewModelScope.launch {


            val result = repository.getData(page,pageNum,sourceType?.id)

            when(result) {

                is Result.Success -> {


                    itemData.value = result.data

                }

                else -> {


                }

            }

        }
    }


}
