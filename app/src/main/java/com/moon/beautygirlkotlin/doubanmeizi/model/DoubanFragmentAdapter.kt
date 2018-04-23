package com.moon.beautygirlkotlin.doubanmeizi.model

import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.moon.beautygirlkotlin.doubanmeizi.DoubanSimpleFragment
import java.util.ArrayList

class DoubanFragmentAdapter(fm : FragmentManager): FragmentStatePagerAdapter(fm) {

    var fragmentList : ArrayList<DoubanSimpleFragment>

    private val titles = arrayOf("大胸妹", "小翘臀", "黑丝袜", "美图控", "高颜值")

    init {
        fragmentList = ArrayList()
        fragmentList.add(DoubanSimpleFragment.getInstance(0))
        fragmentList.add(DoubanSimpleFragment.getInstance(1))
        fragmentList.add(DoubanSimpleFragment.getInstance(2))
        fragmentList.add(DoubanSimpleFragment.getInstance(3))
        fragmentList.add(DoubanSimpleFragment.getInstance(4))
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