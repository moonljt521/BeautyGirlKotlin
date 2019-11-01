package com.moon.beautygirlkotlin.wei1.adapter

import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.databinding.ItemOnlyOneBinding
import com.moon.beautygirlkotlin.listener.ItemClick
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description: 妹子图 adapter
 */
class MeiZiTuAdapter(dataList: MutableList<MeiZiTuBody>) : BaseBindAdapter<ItemOnlyOneBinding, MeiZiTuBody>(dataList), ItemClick<MeiZiTuBody> {

    override fun onClick(view: View, body: MeiZiTuBody) {
        itemListener.onClick(view, body)
    }

    override fun getLayoutId(): Int = R.layout.item_only_one

    override fun bindView(viewHolder: CommonViewHolder<ItemOnlyOneBinding>, position: Int) {
        viewHolder.bindView.body = getDataList()[position]
        viewHolder.bindView.itemClick = itemListener
    }

    lateinit var itemListener: ItemClick<MeiZiTuBody>

}