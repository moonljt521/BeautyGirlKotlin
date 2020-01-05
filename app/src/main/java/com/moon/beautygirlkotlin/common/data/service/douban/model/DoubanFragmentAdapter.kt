package com.moon.beautygirlkotlin.common.data.service.douban.model

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moon.beautygirlkotlin.doubanmeizi.DoubanSimpleFragment
import java.util.*

class DoubanFragmentAdapter(fm : FragmentManager): FragmentStatePagerAdapter(fm) {

    var fragmentList : ArrayList<DoubanSimpleFragment>

    private val titles = arrayOf("大胸妹", "小翘臀", "黑丝袜", "美图控", "高颜值","大杂烩")

    init {
        fragmentList = ArrayList()
        fragmentList.add(DoubanSimpleFragment.getInstance(2))
        fragmentList.add(DoubanSimpleFragment.getInstance(6))
        fragmentList.add(DoubanSimpleFragment.getInstance(7))
        fragmentList.add(DoubanSimpleFragment.getInstance(3))
        fragmentList.add(DoubanSimpleFragment.getInstance(4))
        fragmentList.add(DoubanSimpleFragment.getInstance(5))
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