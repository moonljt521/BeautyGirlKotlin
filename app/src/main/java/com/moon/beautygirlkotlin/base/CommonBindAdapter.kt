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
 * date:   On 2019-11-01 12:29
 * des: 名字由之前的BaseBindAdapter改为了 现在的 CommonBindAdapter ，因为没有兼容multiType 的情况，达不到base 的要求
 */
open class CommonBindAdapter<DB : ViewDataBinding, T>(layoutId: Int, dataList: MutableList<T>) : RecyclerView.Adapter<CommonBindAdapter.CommonViewHolder<DB>>() {

    public var ontItemClick: ItemClick<T>? = null
        get() = field
        set(value) {
            field = value
        }

    private var dataList: MutableList<T> = ArrayList()

    private var layoutId: Int = 0

    init {
        this.dataList = dataList
        this.layoutId = layoutId
    }

    fun getDataList(): MutableList<T> {
        return Collections.unmodifiableList(this.dataList)
    }

    fun removeAtIndex(index: Int) {
        dataList.removeAt(index)
    }

    open fun bindView(viewHolder: CommonViewHolder<DB>, position: Int) {
        viewHolder.bindView.setVariable(BR.body, getDataList()[position])
        ontItemClick?.let {
            viewHolder.bindView.setVariable(BR.itemClick, it)
        }
        viewHolder.bindView.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder<DB> {
        return CommonViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false))
    }

    override fun onBindViewHolder(holder: CommonViewHolder<DB>, position: Int) {
        bindView(holder, position)
    }

    override fun getItemCount(): Int = dataList.size

    class CommonViewHolder<DB : ViewDataBinding>(var bindView: DB) : RecyclerView.ViewHolder(bindView.root)
}