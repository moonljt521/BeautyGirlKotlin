package com.moon.beautygirlkotlin.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.BR
import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.databinding.ItemGirlBinding
import com.moon.beautygirlkotlin.executeAfter
import com.moon.beautygirlkotlin.listener.ItemClick

/**
 * Created by Arthur on 2019-12-29.
 */
class GirlListAdapter  : ListAdapter<GirlData, GirlListAdapter.ItemViewHolder>(DIFF_CALLBACK) {


    public var ontItemClick: ItemClick<GirlData>? = null
        get() = field
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return ItemViewHolder(ItemGirlBinding.inflate(inflater,parent,false))

    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.binding.itemClick = ontItemClick

        holder.binding.executeAfter {

            item = getItem(position)
        }
    }

    class ItemViewHolder(val binding: ItemGirlBinding): RecyclerView.ViewHolder(binding.root) {

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

