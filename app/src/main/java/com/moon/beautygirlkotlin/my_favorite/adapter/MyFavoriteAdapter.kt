package com.moon.beautygirlkotlin.my_favorite.adapter

import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.databinding.ItemFavouriteBinding
import com.moon.beautygirlkotlin.my_favorite.component.ItemMoveListener
import com.moon.beautygirlkotlin.my_favorite.model.MyFavoriteBody
import com.moon.beautygirlkotlin.realm.RealmUtil

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description: 我的收藏 adapt
 */
class MyFavoriteAdapter(dataList: MutableList<MyFavoriteBody>) : BaseBindAdapter<ItemFavouriteBinding, MyFavoriteBody>(dataList)
        , ItemMoveListener {
    override fun bindView(viewHolder: CommonViewHolder<ItemFavouriteBinding>, position: Int) {
        viewHolder.bindView.gankBody = getDataList()[position]
    }

    override fun getLayoutId(): Int = R.layout.item_favourite

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return false
    }

    override fun onItemRemove(position: Int): Boolean {

        RealmUtil.delOneFavourite(getDataList().get(position))

        removeAtIndex(position)

        notifyItemRemoved(position)

        return true
    }

}