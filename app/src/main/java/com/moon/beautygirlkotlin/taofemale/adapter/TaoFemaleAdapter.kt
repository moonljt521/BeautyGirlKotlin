package com.moon.beautygirlkotlin.taofemale.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.taofemale.model.Contentlist
import com.moon.beautygirlkotlin.utils.ImageLoader
import java.util.ArrayList

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description:  淘女郎 adapter
 */
class TaoFemaleAdapter( ) : RecyclerView.Adapter<TaoFemaleAdapter.OrderListHolder>(), View.OnClickListener {

    lateinit var context: Context

    override fun onClick(v: View?) {
        var position : Int = v?.getTag() as Int
        itemListener?.itemClick(v,position)
    }

    var list: ArrayList<Contentlist>? = null

    lateinit var itemListener : ViewItemListener

    init {
        this.list = ArrayList()
    }

    fun loadMoreData(list:List<Contentlist>){
        this.list?.addAll(list)
        notifyDataSetChanged()
    }

    fun refreshData(list:List<Contentlist>){

        if (this.list?.size!! > 0){
            this.list?.clear()
            notifyDataSetChanged()
        }

        this.list?.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: OrderListHolder?, position: Int) {

        var body : Contentlist = list?.get(position)!!



        ImageLoader.loadCircle(context, body.avatarUrl, R.drawable.placeholder_image, holder!!.item_avatar)


        holder?.item_name?.setText(body.realName)

        holder?.item_type?.setText(body.type)
        holder?.item_location?.setText(body.city)
        holder?.item_fans_num?.setText(body.totalFanNum+"")
        holder?.item_height?.setText(body.height+"")
        holder?.item_wight?.setText(body.weight+"")

        ImageLoader.load(context, body.avatarUrl, R.drawable.placeholder_image, holder!!.item_img)

        holder?.item_layout?.setTag(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): OrderListHolder {
        var v : View = LayoutInflater.from(parent?.context)?.inflate(R.layout.item_tao_female,parent,false)!!

        var holder = OrderListHolder(v)

        context = parent?.context!!

        holder?.item_layout?.setOnClickListener(this)

        return holder
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }


    class OrderListHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){

        var item_avatar : ImageView = itemView!!.findViewById<ImageView>(R.id.tao_avatar) as ImageView  // 小圆图

        var item_img : ImageView = itemView!!.findViewById<ImageView>(R.id.tao_image) as ImageView  // 大图

        var item_name: TextView = itemView!!.findViewById<View>(R.id.tao_name) as TextView   // 用户名

        var item_type: TextView = itemView!!.findViewById<View>(R.id.tao_type) as TextView   // 美女类型

        var item_location: TextView = itemView!!.findViewById<View>(R.id.tao_location) as TextView   // 坐标

        var item_fans_num: TextView = itemView!!.findViewById<View>(R.id.tao_fans_num) as TextView   // 粉丝数量

        var item_height: TextView = itemView!!.findViewById<View>(R.id.tao_height) as TextView   // 身高

        var item_wight: TextView = itemView!!.findViewById<View>(R.id.tao_width) as TextView   // 体重

        var item_layout: CardView = itemView!!.findViewById<View>(R.id.tao_root_layout) as CardView


    }


}