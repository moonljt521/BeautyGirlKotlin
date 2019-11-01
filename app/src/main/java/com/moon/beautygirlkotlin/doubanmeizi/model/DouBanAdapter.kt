package com.moon.beautygirlkotlin.doubanmeizi.model

import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.databinding.ItemDoubanBinding
import com.moon.beautygirlkotlin.listener.ItemClick

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description: 豆瓣妹子 adapter
 */
class DouBanAdapter(datalist : MutableList<DoubanMeiziBody> ) : BaseBindAdapter<ItemDoubanBinding,DoubanMeiziBody>(datalist), ItemClick<DoubanMeiziBody> {

    override fun getLayoutId(): Int = R.layout.item_douban

    override fun bindView(viewHolder: CommonViewHolder<ItemDoubanBinding>, position: Int) {
        viewHolder.bindView.body = getDataList()[position]
    }

    override fun onClick(v: View, body: DoubanMeiziBody) {
        itemListener.onClick(v,body)
    }

    var list: ArrayList<DoubanMeiziBody> = ArrayList()

    lateinit var itemListener : ItemClick<DoubanMeiziBody>

    override fun onBindViewHolder(holder: CommonViewHolder<ItemDoubanBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.bindView.itemLayout.setOnClickListener(
                {
                    list.get(position).let { it1 -> itemListener.onClick(holder.bindView.itemLayout, it1) }
                }
        )
    }

}