package com.moon.beautygirlkotlin.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

/**
 * author: jiangtao.liang
 * date:   On 2019-11-01 12:29
 */
abstract class BaseBindAdapter<DB : ViewDataBinding , T>(context: Context ,dataList : MutableList<T>) : RecyclerView.Adapter<BaseBindAdapter.CommonViewHolder<DB>>(){

    private var dataList : MutableList<T> = ArrayList()
    private var context : Context

    init {
        this.dataList = dataList
        this.context = context
    }

    fun getDataList() : MutableList<T>{
        return Collections.unmodifiableList(this.dataList)
    }

    fun refreshData(data: MutableList<T>){
        if (!this.dataList.isEmpty()){
            this.dataList.clear()
            notifyDataSetChanged()
        }
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun loadMoreData(data: List<T>) {
        this.dataList.addAll(data)
        notifyDataSetChanged()
    }

    abstract fun getLayoutId() : Int

    abstract fun bindView(viewHolder: CommonViewHolder<DB> , position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<DB> {
        return CommonViewHolder(DataBindingUtil.inflate(LayoutInflater.from(context),getLayoutId(),parent,false))
    }

    override fun onBindViewHolder(holder: CommonViewHolder<DB>, position: Int) {
       bindView(holder , position)
    }

    override fun getItemCount(): Int = dataList.size

    class CommonViewHolder<DB : ViewDataBinding>(var bindView: DB) : RecyclerView.ViewHolder(bindView.root)

}