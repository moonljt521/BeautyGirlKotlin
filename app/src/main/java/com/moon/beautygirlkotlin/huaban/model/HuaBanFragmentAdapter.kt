package com.moon.beautygirlkotlin.huaban.model

import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.moon.beautygirlkotlin.doubanmeizi.DoubanSimpleFragment
import com.moon.beautygirlkotlin.huaban.HuaBanSimpleFragment
import java.util.ArrayList

class HuaBanFragmentAdapter(fm : FragmentManager): FragmentStatePagerAdapter(fm) {

    var fragmentList : ArrayList<HuaBanSimpleFragment>

    private val titles = arrayOf("大胸妹", "小清新", "文艺范", "性感妹", "大长腿", "黑丝袜", "小翘臀")

    init {
        fragmentList = ArrayList()
        fragmentList.add(HuaBanSimpleFragment.getInstance(34))
        fragmentList.add(HuaBanSimpleFragment.getInstance(35))
        fragmentList.add(HuaBanSimpleFragment.getInstance(36))
        fragmentList.add(HuaBanSimpleFragment.getInstance(37))
        fragmentList.add(HuaBanSimpleFragment.getInstance(38))
        fragmentList.add(HuaBanSimpleFragment.getInstance(39))
        fragmentList.add(HuaBanSimpleFragment.getInstance(40))
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