package com.moon.beautygirlkotlin.test

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.moon.beautygirlkotlin.data.entity.Source

/**
 * Created by Arthur on 2019-12-29.
 */
class GirlFragmentAdapter(fm: FragmentManager, private val source: Source):
        FragmentStatePagerAdapter
(fm) {

    val titles = source.types?.map {
        it.title
    } ?: listOf("")


    val fragmentList = source.types?.map {

        GirlListFragment.newInstance(source,it)
    } ?: listOf(GirlListFragment.newInstance(source,null))

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