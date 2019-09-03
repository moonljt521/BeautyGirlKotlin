package com.moon.beautygirlkotlin.wei1.adapter

import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.moon.beautygirlkotlin.wei1.MeiZiTuSimpleFragment
import java.util.*

/**
 * 妹子图  Fragment adapter
 *
 */
class MeiZiTuFragmentAdapter(fm : FragmentManager): FragmentStatePagerAdapter(fm) {

    var fragmentList : ArrayList<MeiZiTuSimpleFragment>

    private val titles = arrayOf("明星相关专辑")

    init {
        fragmentList = ArrayList()
        fragmentList.add(MeiZiTuSimpleFragment.getInstance("list_18_"))
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