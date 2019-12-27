package com.moon.beautygirlkotlin.data.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * 也可以只传递一个source
 * Created by Arthur on 2019-12-28.
 */
class GirlViewModel(private val repository: GirlRepository): ViewModel() {


    //
//    val serviceContext

    fun loadData() = viewModelScope.launch {

        withContext(Dispatchers.IO) {
            repository.getDatas(0,10)

        }
    }

}