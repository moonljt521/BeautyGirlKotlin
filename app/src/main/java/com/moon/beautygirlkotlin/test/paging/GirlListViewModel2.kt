package com.moon.beautygirlkotlin.test.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.moon.beautygirlkotlin.data.entity.Event
import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.Result
import com.moon.beautygirlkotlin.data.entity.SourceType
import com.moon.beautygirlkotlin.data.test.GirlRepository
import com.moon.beautygirlkotlin.listener.ItemClick
import com.moon.beautygirlkotlin.utils.Logger
import kotlinx.coroutines.*

/**
 * Created by Arthur on 2019-12-30.
 */
class GirlListViewModel2(val repository: GirlRepository, val sourceType: SourceType?) :
        ViewModel(), ItemClick<GirlData> {


    val itemEvent = MutableLiveData<Event<GirlData>>()
    val swipeRefreshing = MutableLiveData<Boolean>()


    val factory = PageKeyDataSourceFactory(repository, sourceType)

    val livePagedListBuilder = LivePagedListBuilder(factory,
            PagedList.Config.Builder()
                    .setPageSize(10)
                    .setPrefetchDistance(5)
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(10)
                    .build())
            .build()

    fun refresh() {

        factory.dataSource.invalidate()

    }
    override fun onClick(body: GirlData) {

        itemEvent.value = Event(body)
    }




}

class CustomPageKeyDataSource(val repository: GirlRepository, val sourceType: SourceType?) : PageKeyedDataSource<Int, GirlData>() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)



    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GirlData>) {


        scope.launch {

            try {
                Logger.i("wy", "Initial params = ${params.requestedLoadSize}")

                val result = repository.getData(1, params.requestedLoadSize, sourceType?.id)

                when (result) {

                    is Result.Success -> {

                        //计算count？----- 页码从 0 还是1 开始呢

                        //next page key -》 0 + （20/10）

                        //有可能造成重复
                        callback.onResult(result.data, null, 1)
                    }

                }

            } catch (e: Exception) {


            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GirlData>) {

        scope.launch {

            try {

                Logger.i("wy", "after params = ${params.key}, ${params.requestedLoadSize}")

                val result = repository.getData(params.key, params.requestedLoadSize, sourceType?.id)

                when (result) {

                    is Result.Success -> {
                        callback.onResult(result.data, params.key + 1)
                    }

                }

            } catch (e: Exception) {


            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GirlData>) {

        scope.launch {

            try {

                Logger.i("wy", "before params = ${params.key}, ${params.requestedLoadSize}")

                val result = repository.getData(params.key, params.requestedLoadSize, sourceType?.id)

                when (result) {

                    is Result.Success -> {
                        callback.onResult(result.data, params.key - 1)
                    }

                }

            } catch (e: Exception) {


            }
        }
    }

    override fun invalidate() {
        super.invalidate()

        scope.cancel()
    }

}

class PageKeyDataSourceFactory(private val repository: GirlRepository,private  val sourceType:
SourceType?) :
        DataSource.Factory<Int, GirlData>() {
    lateinit var dataSource: DataSource<Int,GirlData>

    override fun create(): DataSource<Int, GirlData> {

        dataSource =  CustomPageKeyDataSource(repository, sourceType)

        return dataSource
    }

}

