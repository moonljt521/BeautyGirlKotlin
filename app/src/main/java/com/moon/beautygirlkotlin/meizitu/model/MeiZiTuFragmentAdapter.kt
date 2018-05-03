package com.moon.beautygirlkotlin.meizitu.model

import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.moon.beautygirlkotlin.meizitu.MeiZiTuSimpleFragment
import java.util.*

/**
 * 妹子图  Fragment adapter
 *
 */
class MeiZiTuFragmentAdapter(fm : FragmentManager): FragmentStatePagerAdapter(fm) {

    var fragmentList : ArrayList<MeiZiTuSimpleFragment>

    private val titles = arrayOf("热门", "推荐", "清纯", "台湾", "日本", "性感")

    init {
        fragmentList = ArrayList()
        fragmentList.add(MeiZiTuSimpleFragment.getInstance("hot"))
        fragmentList.add(MeiZiTuSimpleFragment.getInstance("best"))
        fragmentList.add(MeiZiTuSimpleFragment.getInstance("mm"))
        fragmentList.add(MeiZiTuSimpleFragment.getInstance("taiwan"))
        fragmentList.add(MeiZiTuSimpleFragment.getInstance("japan"))
        fragmentList.add(MeiZiTuSimpleFragment.getInstance("xinggan"))
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titles[position]
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        // 空实现，不写super
    }

    override fun getCount(): Int {
        return titles.size
    }
}