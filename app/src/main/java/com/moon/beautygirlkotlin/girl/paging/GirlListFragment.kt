package com.moon.beautygirlkotlin.girl.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.moon.beautygirlkotlin.common.data.entity.EventObserver
import com.moon.beautygirlkotlin.common.data.entity.Source
import com.moon.beautygirlkotlin.common.data.entity.SourceType
import com.moon.beautygirlkotlin.databinding.GirlListFragmentBinding
import com.moon.beautygirlkotlin.preview.ViewLargeImgActivity

/**
 * Created by Arthur on 2019-12-30.
 */
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


    val viewModel: GirlListViewModel by lazy {

        val factory = GirlListViewModelFactory(source, sourceType)
        ViewModelProviders.of(this, factory).get(GirlListViewModel::class
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


        val  mAdapter = GirlListPageAdapter(viewModel)

        val  mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        dataBinding.commonRecyclerView.apply {
            animation = null
            layoutManager = mLayoutManager
//            addOnScrollListener(OnLoadMoreListener())
            adapter = mAdapter
//            setOnTouchListener { _, motionEvent -> mIsRefreshing }
        }


        viewModel.livePagedListBuilder.observe(viewLifecycleOwner, Observer {

            mAdapter.submitList(it)

            dataBinding.commonSwipeRefresh.isRefreshing = false
        })

        viewModel.itemEvent.observe(viewLifecycleOwner,EventObserver {
            ViewLargeImgActivity.startViewBigImaActivity(requireContext(), it.url,
                    it.text, true)
        })

        dataBinding.commonSwipeRefresh.setOnRefreshListener {

            viewModel.refresh()
        }

    }

}