package com.moon.beautygirlkotlin.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.BR
import com.moon.beautygirlkotlin.listener.ItemClick
import java.util.*
import kotlin.collections.ArrayList

/**
 * author: jiangtao.liang
 * date:   On 2019-11-25 12:30
 * des ： 支持多类型itemView的 adapter ，需要覆写 createViewType ，加入 xml
 */
abstract class BaseBindAdapter<DB : ViewDataBinding, T>(dataList: MutableList<T>) : RecyclerView.Adapter<BaseBindAdapter.BaseHolder<T>>() {

    var ontItemClick: ItemClick<T>? = null
        get() = field
        set(value) {
            field = value
        }

    private var dataList: MutableList<T> = ArrayList()

    init {
        this.dataList = dataList
    }

    fun removeAtIndex(index: Int) {
        dataList.removeAt(index)
    }

    fun getDataList(): MutableList<T> {
        return Collections.unmodifiableList(this.dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        return BaseHolder<T>(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)).create()
    }

    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.bindTo(dataList[position])
    }

    abstract fun createViewType(position: Int): Int

    override fun getItemViewType(position: Int): Int {
        return createViewType(position)
    }

    override fun getItemCount(): Int = dataList.size

    class BaseHolder<T>(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(data: T) {
            binding.setVariable(BR.body, data)
            binding.executePendingBindings()
        }

        fun create(): BaseHolder<T> {
            return BaseHolder(binding)
        }
    }
}