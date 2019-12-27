package com.moon.beautygirlkotlin.my_favorite.component

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by moon on 2017/9/8.
 */

class MyItemTouchHelperCallBack(internal var mItemMoveListener: ItemMoveListener) : ItemTouchHelper.Callback() {


    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN

        // 如果你不想左右滑动，可以将 swipeFlags = 0
        var swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        if (!mItemMoveListener.canMove(viewHolder.layoutPosition)){
            swipeFlags = 0
        }

        //最终的动作标识（flags）必须要用makeMovementFlags()方法生成
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return mItemMoveListener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mItemMoveListener.onItemRemove(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

}
