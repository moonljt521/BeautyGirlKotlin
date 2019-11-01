package com.moon.beautygirlkotlin.gank.adapter

import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.databinding.ItemMengMeiziBinding
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.beautygirlkotlin.listener.ItemClick

/**
 * author: jiangtao.liang
 * date:   On 2019-11-01 10:57
 */
class GankAdapter(dataList: MutableList<GankMeiziBody>) : BaseBindAdapter<ItemMengMeiziBinding, GankMeiziBody>( dataList) , ItemClick<GankMeiziBody>{

    lateinit var itemClick : ItemClick<GankMeiziBody> ;

    override fun onClick(view: View, body: GankMeiziBody) {
        itemClick.onClick(view,body)
    }

    override fun bindView(viewHolder: CommonViewHolder<ItemMengMeiziBinding>, position: Int) {
        viewHolder.bindView.gankBody = getDataList().get(position)
        viewHolder.bindView.itemClick = itemClick
    }

    override fun getLayoutId(): Int = R.layout.item_meng_meizi
}