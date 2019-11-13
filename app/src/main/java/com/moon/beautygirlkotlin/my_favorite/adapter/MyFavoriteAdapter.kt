package com.moon.beautygirlkotlin.my_favorite.adapter

import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.databinding.ItemFavouriteBinding
import com.moon.beautygirlkotlin.my_favorite.FavouriteItemClick
import com.moon.beautygirlkotlin.my_favorite.component.ItemMoveListener
import com.moon.beautygirlkotlin.room.FavoriteBean

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description: 我的收藏 adapt
 */
class MyFavoriteAdapter(layoutId: Int, dataList: MutableList<FavoriteBean>) : BaseBindAdapter<ItemFavouriteBinding, FavoriteBean>(layoutId, dataList)
        , ItemMoveListener {

    var listener : FavouriteItemClick<FavoriteBean> ? = null

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return false
    }

    override fun onItemRemove(position: Int): Boolean {

        listener?.onClick(getDataList()[position])

        removeAtIndex(position)

        notifyItemRemoved(position)

        return true
    }

}