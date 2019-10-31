package com.moon.beautygirlkotlin.doubanmeizi.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.databinding.ItemDoubanBinding
import com.moon.beautygirlkotlin.listener.ItemClick

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description: 豆瓣妹子 adapter
 */
class DouBanAdapter( ) : RecyclerView.Adapter<DouBanAdapter.OrderListHolder>(), ItemClick<DoubanMeiziBody> {

    override fun onClick(v: View, body: DoubanMeiziBody) {
        itemListener.onClick(v,body)
    }

    lateinit var context: Context

    var list: ArrayList<DoubanMeiziBody> = ArrayList()

    lateinit var itemListener : ItemClick<DoubanMeiziBody>

    fun loadMoreData(list:List<DoubanMeiziBody>){
        this.list.addAll(list)
        notifyItemInserted(list.size)
    }

    fun refreshData(list:List<DoubanMeiziBody>){

        if (this.list.size > 0){
            this.list.clear()
            notifyDataSetChanged()
        }

        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: OrderListHolder, position: Int) {

        holder.binding.body = list[position]

        holder.binding.itemLayout.setOnClickListener(
                {
                    list.get(position).let { it1 -> itemListener.onClick(holder.binding.itemLayout, it1) }
                }
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListHolder {
        val binding = ItemDoubanBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return OrderListHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class OrderListHolder(val binding: ItemDoubanBinding) : RecyclerView.ViewHolder(binding.root)
}