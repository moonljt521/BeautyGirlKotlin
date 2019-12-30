package com.moon.beautygirlkotlin.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.entity.SourceType
import com.moon.beautygirlkotlin.data.test.GirlRepository
import com.moon.beautygirlkotlin.listener.ItemClick
import kotlinx.coroutines.launch

class GirlListViewModel(val repository: GirlRepository, val sourceType: SourceType?) : ViewModel() , ItemClick<GirlData> {

    val item: MutableLiveData<GirlData> by lazy {
        MutableLiveData<GirlData>()
    }

    override fun onClick(body: GirlData) {
        item.postValue(body)
    }


    val list = ArrayList<GirlData>()

    val itemData = MutableLiveData<List<GirlData>>()

    fun loaData(page: Int, pageSize: Int) {

        viewModelScope.launch {

            val result = repository.getData(page, pageSize, sourceType?.id)

            when (result) {

                is Result.Success -> {

                    if (page == 1) {
                        list.clear()
                    }
                    list.addAll(result.data)

                    itemData.value = result.data
                }

                else -> {


                }

            }

        }
    }


}
