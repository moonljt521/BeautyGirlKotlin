package com.moon.beautygirlkotlin.my_favorite.component;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by moon on 2017/9/8.
 */

public class MyItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    ItemMoveListener mItemMoveListener;

    public MyItemTouchHelperCallBack(ItemMoveListener itemMoveListener) {
        mItemMoveListener = itemMoveListener;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        // 如果你不想左右滑动，可以将 swipeFlags = 0
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        //最终的动作标识（flags）必须要用makeMovementFlags()方法生成
        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return mItemMoveListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mItemMoveListener.onItemRemove(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

}
