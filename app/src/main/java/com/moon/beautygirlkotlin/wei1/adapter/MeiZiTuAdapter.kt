package com.moon.beautygirlkotlin.wei1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.databinding.ItemOnlyOneBinding
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.listener.ItemClick

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description: 妹子图 adapter
 */
class MeiZiTuAdapter( ) : RecyclerView.Adapter<MeiZiTuAdapter.OrderListHolder>(), View.OnClickListener {

    lateinit var context: Context


    var list: ArrayList<MeiZiTuBody> = ArrayList()

    lateinit var itemListener : ItemClick<MeiZiTuBody>

    override fun onClick(v: View?) {
        val position : Int = v?.getTag() as Int
        list.get(position).let { itemListener.onClick(v, it) }
    }

    fun loadMoreData(list:List<MeiZiTuBody>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun refreshData(list:List<MeiZiTuBody>){
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

        val binding = ItemOnlyOneBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return OrderListHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class OrderListHolder(val binding: ItemOnlyOneBinding) : RecyclerView.ViewHolder(binding.root)
}