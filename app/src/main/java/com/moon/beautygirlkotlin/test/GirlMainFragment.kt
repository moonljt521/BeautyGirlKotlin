package com.moon.beautygirlkotlin.test


import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.data.entity.Source
import com.moon.beautygirlkotlin.databinding.FragmentGirlMainBinding

/**
 * A simple [Fragment] subclass.
 */
class GirlMainFragment : BaseFragment() {

    companion object {

        const val ARGS_SOURCE = "args_source"

        fun newInstance(source:Source): GirlMainFragment = GirlMainFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARGS_SOURCE,source)
            }
        }
    }

    val source: Source by lazy {

        val source = arguments?.getParcelable<Source>(ARGS_SOURCE)

        source!!
    }

    lateinit var databinding: FragmentGirlMainBinding

    val viewModel: GirlMainViewModel by lazy{

        ViewModelProviders.of(this, GirlMainViewModelFactory(source)).get(GirlMainViewModel::class
                .java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        databinding = FragmentGirlMainBinding.inflate(inflater,container,false).apply {

            viewmodel = viewModel
            setLifecycleOwner(viewLifecycleOwner)
        }

        return databinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //注册一下

        val pageMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, resources
                .displayMetrics).toInt()

        databinding.doubanViewpager.apply {
            offscreenPageLimit = 6
            setPageMargin(pageMargin)
            adapter = GirlFragmentAdapter(childFragmentManager,source )
            databinding.doubanTablayout.setupWithViewPager(this)
        }
    }



    override fun initData() {
    }

    override fun initViews(view: View?) {
    }


}
