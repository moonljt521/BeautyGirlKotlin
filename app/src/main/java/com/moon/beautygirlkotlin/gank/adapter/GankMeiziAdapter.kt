package com.moon.beautygirlkotlin.gank.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.databinding.ItemMengMeiziBinding
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.widget.ItemClick
import java.util.*

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description:
 */
class GankMeiziAdapter ( ) : RecyclerView.Adapter<GankMeiziAdapter.GankItemViewHolder>(), View.OnClickListener{

    lateinit var context: Context

    override fun onClick(v: View?) {
        val position : Int = v?.getTag() as Int
        list?.get(position)?.let { itemListener.onClick(v, it) }
    }

    var list: ArrayList<GankMeiziBody>? = null

    lateinit var itemListener : ItemClick

    init {
        this.list = ArrayList()
    }

    fun loadMoreData(list:List<GankMeiziBody>){
        this.list?.addAll(list)
        notifyItemInserted(list.size)
    }

    fun refreshData(list:List<GankMeiziBody>){

        if (this.list?.size!! > 0){
            this.list?.clear()
            notifyDataSetChanged()
        }

        this.list?.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: GankItemViewHolder, position: Int) {

        holder.binding.gankBody = list?.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GankItemViewHolder {
        context = parent.context!!

        val binding = ItemMengMeiziBinding.inflate(LayoutInflater.from(parent.context),parent,false)

//        binding.itemClick = itemListener

        return GankItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    class GankItemViewHolder(val binding: ItemMengMeiziBinding) : RecyclerView.ViewHolder(binding.root){

    }

}
