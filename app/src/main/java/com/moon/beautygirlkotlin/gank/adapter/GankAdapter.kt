package com.moon.beautygirlkotlin.gank.adapter

import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.databinding.ItemMengMeiziBinding
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody

/**
 * author: jiangtao.liang
 * date:   On 2019-11-01 10:57
 */
class GankAdapter(dataList: MutableList<GankMeiziBody>) : BaseBindAdapter<ItemMengMeiziBinding, GankMeiziBody>( dataList) {

    override fun bindView(viewHolder: CommonViewHolder<ItemMengMeiziBinding>, position: Int) {
        viewHolder.bindView.gankBody = getDataList().get(position)
    }

    override fun getLayoutId(): Int = R.layout.item_meng_meizi
}