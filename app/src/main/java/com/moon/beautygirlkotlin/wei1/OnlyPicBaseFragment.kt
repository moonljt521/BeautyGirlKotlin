package com.moon.beautygirlkotlin.wei1

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.databinding.FragmentBaseMeizituBinding
import com.moon.beautygirlkotlin.wei1.adapter.MeiZiTuFragmentAdapter

/**
 * 【唯一】图库   fragment
 */
class OnlyPicBaseFragment : BaseFragment()
{

    private lateinit var binding : FragmentBaseMeizituBinding

    private lateinit var viewModel : BaseViewModel<*>

    companion object {

        fun getInstance(id: Int): OnlyPicBaseFragment {
            val fragment = OnlyPicBaseFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)
        binding = FragmentBaseMeizituBinding.inflate(inflater,container,false)
        binding.apply {
            viewModel = this@OnlyPicBaseFragment.viewModel
            setLifecycleOwner(viewLifecycleOwner)
        }
        return binding.root
    }

    /**
     * 初始化
     */
    override fun initData() {
        val pageMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources
                .displayMetrics).toInt()

        binding.meizituViewpager.apply {
            offscreenPageLimit = 6
            adapter = MeiZiTuFragmentAdapter(childFragmentManager)
            setPageMargin(pageMargin)
        }

        binding.meizituTablayout.apply {
            setupWithViewPager(binding.meizituViewpager)
            tabMode = MODE_SCROLLABLE
        }
    }

    override fun initViews(view: View?) {

    }
}