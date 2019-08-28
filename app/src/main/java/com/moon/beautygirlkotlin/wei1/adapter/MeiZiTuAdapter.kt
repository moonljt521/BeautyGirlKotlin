package com.moon.beautygirlkotlin.wei1.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.utils.ImageLoader
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import java.util.*

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description: 妹子图 adapter
 */
class MeiZiTuAdapter( ) : RecyclerView.Adapter<MeiZiTuAdapter.OrderListHolder>(), View.OnClickListener {

    lateinit var context: Context

    override fun onClick(v: View?) {
        val position : Int = v?.getTag() as Int
        itemListener.itemClick(v,position)
    }

    var list: ArrayList<MeiZiTuBody>? = null

    lateinit var itemListener : ViewItemListener

    init {
        this.list = ArrayList()
    }

    fun loadMoreData(list:List<MeiZiTuBody>){
        this.list?.addAll(list)
        notifyItemInserted(list.size)
    }

    fun refreshData(list:List<MeiZiTuBody>){

        if (this.list?.size!! > 0){
            this.list?.clear()
            notifyDataSetChanged()
        }

        this.list?.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: OrderListHolder?, position: Int) {

        val body : MeiZiTuBody = list?.get(position)!!


        ImageLoader.load(context, body.imageurl, R.drawable.placeholder_image, holder!!.item_img)


        holder.item_title.setText(body.title)


        holder.item_layout.setTag(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OrderListHolder {
        val v : View = LayoutInflater.from(parent?.context)?.inflate(R.layout.item_meng_meizi,parent,false)!!

        val holder = OrderListHolder(v)

        context = parent?.context!!

        holder.item_layout.setOnClickListener(this)

        return holder
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }


    class OrderListHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){

        var item_img : ImageView = itemView!!.findViewById<ImageView>(R.id.item_img) as ImageView

        var item_title: TextView = itemView!!.findViewById<View>(R.id.item_title) as TextView

        var item_layout: LinearLayout = itemView!!.findViewById<View>(R.id.item_layout) as LinearLayout
    }
}