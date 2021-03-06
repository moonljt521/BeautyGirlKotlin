package com.moon.beautygirlkotlin.girl.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.common.data.entity.GirlData
import com.moon.beautygirlkotlin.common.listener.ItemClick
import com.moon.beautygirlkotlin.common.utils.executeAfter
import com.moon.beautygirlkotlin.databinding.ItemGirlBinding

/**
 * Created by Arthur on 2019-12-30.
 */
class GirlListPageAdapter(private val itemClick: ItemClick<GirlData>) : PagedListAdapter<GirlData,
        GirlListPageAdapter.ItemViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlListPageAdapter.ItemViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return GirlListPageAdapter.ItemViewHolder(ItemGirlBinding.inflate(inflater, parent, false))

    }


    override fun onBindViewHolder(holder: GirlListPageAdapter.ItemViewHolder, position: Int) {

        holder.binding.executeAfter {

            itemClick = this@GirlListPageAdapter.itemClick

            item = getItem(position)
        }
    }

    class ItemViewHolder(val binding: ItemGirlBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GirlData>() {
            override fun areItemsTheSame(oldData: GirlData, newData: GirlData): Boolean {

                return oldData == newData
            }

            override fun areContentsTheSame(oldData: GirlData, newData: GirlData): Boolean {

                return oldData == newData
            }
        }
    }
}