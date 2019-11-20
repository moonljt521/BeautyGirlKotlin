package com.moon.beautygirlkotlin.doubanmeizi

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.base.BaseViewModel
import com.moon.beautygirlkotlin.databinding.FragmentBaseDoubanMeiziBinding
import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanFragmentAdapter

/**
 * 豆瓣模块 base  fragment
 */
class DouBanBaseFragment : BaseFragment() {

    private lateinit var binding : FragmentBaseDoubanMeiziBinding

    private lateinit var viewModel : BaseViewModel<*>

    companion object {

        fun getInstance(id: Int): DouBanBaseFragment {
            val fragment = DouBanBaseFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)
        binding = FragmentBaseDoubanMeiziBinding.inflate(inflater,container,false)
        binding.apply {
            viewModel = this@DouBanBaseFragment.viewModel
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

        binding.doubanViewpager.apply {
            offscreenPageLimit = 6
            setPageMargin(pageMargin)
            adapter = DoubanFragmentAdapter(childFragmentManager)
            binding.doubanTablayout.setupWithViewPager(this)
        }
    }

    override fun initViews(view: View?) {

    }

}