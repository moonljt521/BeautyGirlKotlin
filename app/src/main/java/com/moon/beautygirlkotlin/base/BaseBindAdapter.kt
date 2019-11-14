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
 * date:   On 2019-11-01 12:29
 */
open class BaseBindAdapter<DB : ViewDataBinding , T>(layoutId : Int ,dataList : MutableList<T>) : RecyclerView.Adapter<BaseBindAdapter.CommonViewHolder<DB>>() {

    var ontItemClick : ItemClick<T> ? = null

    private var dataList : MutableList<T> = ArrayList()

    private var layoutId:Int = 0

    init {
        this.dataList = dataList
        this.layoutId = layoutId
    }

    fun getDataList() : MutableList<T>{
        return Collections.unmodifiableList(this.dataList)
    }

    fun removeAtIndex(index : Int){
        dataList.removeAt(index)
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

    open fun bindView(viewHolder: CommonViewHolder<DB>, position: Int){
        viewHolder.bindView.setVariable(BR.body,getDataList()[position])
        viewHolder.bindView.executePendingBindings()
        viewHolder.itemView.setOnClickListener {
            ontItemClick?.onClick(it,getDataList()[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<DB> {
        return CommonViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),layoutId,parent,false))
    }

    open override fun onBindViewHolder(holder: CommonViewHolder<DB>, position: Int) {
       bindView(holder , position)
    }

    override fun getItemCount(): Int = dataList.size

    class CommonViewHolder<DB : ViewDataBinding>(var bindView: DB) : RecyclerView.ViewHolder(bindView.root)

}