package com.moon.beautygirlkotlin.my_favorite.adapter

import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.base.CommonBindAdapter
import com.moon.beautygirlkotlin.databinding.ItemFavouriteBinding
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.beautygirlkotlin.my_favorite.FavouriteItemClick
import com.moon.beautygirlkotlin.my_favorite.component.ItemMoveListener
import com.moon.beautygirlkotlin.room.FavoriteBean
import com.moon.beautygirlkotlin.room.FavoriteBeanOther

/**
 * author: moon
 * created on: 18/4/4 下午4:37
 * description: 我的收藏 adapt
 */
class MyFavoriteAdapter( dataList: MutableList<Any>) : BaseBindAdapter<ItemFavouriteBinding, Any>(dataList)
        , ItemMoveListener {


    override fun createViewType(position: Int): Int {
        val t = getDataList()[position]
        if (t is FavoriteBean) return R.layout.item_favourite
        if (t is FavoriteBeanOther) return R.layout.item_favourite_other
        return 0
    }

    var listener : FavouriteItemClick<Any> ? = null

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