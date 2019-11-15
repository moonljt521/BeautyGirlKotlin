package com.moon.beautygirlkotlin.wei1.adapter

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moon.beautygirlkotlin.wei1.OnlyOneSimpleFragment
import java.util.*

/**
 * 妹子图  Fragment adapter
 *
 */
class MeiZiTuFragmentAdapter(fm : FragmentManager): FragmentStatePagerAdapter(fm) {

    var fragmentList : ArrayList<OnlyOneSimpleFragment>

    private val titles = arrayOf("明星相关专辑")

    init {
        fragmentList = ArrayList()
        fragmentList.add(OnlyOneSimpleFragment.getInstance("list_18_"))
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