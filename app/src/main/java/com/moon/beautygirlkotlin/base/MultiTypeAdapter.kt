package com.moon.beautygirlkotlin.base

import android.view.LayoutInflater
import android.view.ViewGroup

import java.util.ArrayList

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

/**
 * author: jiangtao.liang
 * date:   On 2019-11-19 11:03
 */
class MultiTypeAdapter : RecyclerView.Adapter<MultiTypeAdapter.ItemViewHolder>() {
    private val items = ArrayList<IItem>()

    interface IItem {
        // should directly return layout
        val type: Int
    }

    ///////////////////////////////////////////////////////
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent, viewType)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindTo(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    ///////////////////////////////////////////////////////
    // operate items
    fun getItems(): List<IItem> {
        return items
    }

    fun setItem(item: IItem) {
        clearItems()
        addItem(item)
    }

    fun setItems(items: List<IItem>) {
        clearItems()
        addItems(items)
    }

    fun addItem(item: IItem) {
        items.add(item)
    }

    fun addItem(item: IItem, index: Int) {
        items.add(index, item)
    }

    fun addItems(items: List<IItem>) {
        this.items.addAll(items)
    }

    fun removeItem(item: IItem) {
        items.remove(item)
    }

    fun clearItems() {
        items.clear()
    }

    ///////////////////////////////////////////////////
    class ItemViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(item: MultiTypeAdapter.IItem) {
            binding.setVariable(BR.body, item)
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup, viewType: Int): ItemViewHolder {
                val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context),
                        viewType, parent, false)
                return ItemViewHolder(binding)
            }
        }
    }
}