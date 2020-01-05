package com.moon.beautygirlkotlin.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.BR
import com.moon.beautygirlkotlin.common.listener.ItemClick
import java.util.*
import kotlin.collections.ArrayList

/**
 * author: jiangtao.liang
 * date:   On 2019-11-25 12:30
 * des ： 支持多类型itemView的 adapter ，需要覆写 createViewType ，加入 xml
 */
abstract class BaseBindAdapter(dataList: MutableList<Any> , viewType: ViewTypeCallBack) : RecyclerView.Adapter<BaseBindAdapter.BaseHolder<Any>>() {

    interface ViewTypeCallBack {
        fun createViewType(position: Int): Int
    }

    var ontItemClick: ItemClick<Any>? = null
        get() = field
        set(value) {
            field = value
        }

    private var viewTypeCallBack : ViewTypeCallBack? = null

    private var dataList: MutableList<Any> = ArrayList()

    init {
        this.dataList = dataList
        this.viewTypeCallBack = viewType
    }

    fun removeAtIndex(index: Int) {
        dataList.removeAt(index)
    }

    fun getDataList(): MutableList<Any> {
        return Collections.unmodifiableList(this.dataList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Any> {
        return BaseHolder<Any>(DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)).create()
    }

    override fun onBindViewHolder(holder: BaseHolder<Any>, position: Int) {
        holder.bindTo(dataList[position])
    }

//    abstract fun createViewType(position: Int): Int

    override fun getItemViewType(position: Int): Int {
        return this.viewTypeCallBack?.createViewType(position) ?: 0
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