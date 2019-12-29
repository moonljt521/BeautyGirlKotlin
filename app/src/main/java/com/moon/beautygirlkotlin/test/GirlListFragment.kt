package com.moon.beautygirlkotlin.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.moon.beautygirlkotlin.data.entity.Source
import com.moon.beautygirlkotlin.data.entity.SourceType
import com.moon.beautygirlkotlin.databinding.GirlListFragmentBinding
import com.moon.beautygirlkotlin.utils.Logger

class GirlListFragment : Fragment() {

    companion object {

        const val ARGS_SOURCE = "args_source"
        const val ARGS_SOURCE_TYPE = "args_source_type"


        fun newInstance(source: Source, sourceType: SourceType?) = GirlListFragment().apply {

            arguments = Bundle().apply {
                putParcelable(ARGS_SOURCE,source)
                putParcelable(ARGS_SOURCE_TYPE,sourceType)
            }
        }
    }

    val source: Source by lazy {
        val source = arguments?.getParcelable<Source>(ARGS_SOURCE)
        source!!
    }

    val sourceType: SourceType? by lazy {
        val sourceType = arguments?.getParcelable<SourceType>(ARGS_SOURCE_TYPE)
        sourceType

    }


    val viewModel: GirlListViewModel by lazy{

        ViewModelProviders.of(this, GirlListViewModelFactory(source,sourceType)).get(GirlListViewModel::class
                .java)
    }


    lateinit var dataBinding: GirlListFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        dataBinding = GirlListFragmentBinding.inflate(inflater,container,false).apply {

            setLifecycleOwner(viewLifecycleOwner)
        }
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


      val  mAdapter: GirlListAdapter = GirlListAdapter()

      val  mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        dataBinding.commonRecyclerView.apply {
            animation = null
            layoutManager = mLayoutManager
//            addOnScrollListener(OnLoadMoreListener())
            adapter = mAdapter
//            setOnTouchListener { _, motionEvent -> mIsRefreshing }
        }

        viewModel.itemData.observe(viewLifecycleOwner, Observer {
//            ViewBigImgActivity.startViewBigImaActivity(requireContext(), it.url,
//                    it.title, true)
            Logger.d("observe,result ${it}")

            mAdapter.submitList(it)
        })

        viewModel.loaData(0,20)
    }

}
