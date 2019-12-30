package com.moon.beautygirlkotlin.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.moon.beautygirlkotlin.data.entity.GirlData
import com.moon.beautygirlkotlin.data.entity.Source
import com.moon.beautygirlkotlin.data.entity.SourceType
import com.moon.beautygirlkotlin.databinding.GirlListFragmentBinding
import com.moon.beautygirlkotlin.utils.Logger
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity

class GirlListFragment : Fragment(), Observer<List<GirlData>> {

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageSize: Int = 10

    var hasMoreData = true

    // 懒加载
    protected var fragmentIsVisible: Boolean = false

    protected var isExcuteUserVisible: Boolean = false

    protected var isPrepared: Boolean = false

    protected var loadFinish: Boolean = false

    val mAdapter: GirlListAdapter = GirlListAdapter()

    companion object {

        const val ARGS_SOURCE = "args_source"
        const val ARGS_SOURCE_TYPE = "args_source_type"

        fun newInstance(source: Source, sourceType: SourceType?) = GirlListFragment().apply {

            arguments = Bundle().apply {
                putParcelable(ARGS_SOURCE, source)
                putParcelable(ARGS_SOURCE_TYPE, sourceType)
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
        ViewModelProviders.of(this, GirlListViewModelFactory(source, sourceType)).get(GirlListViewModel::class
                .java)
    }

    lateinit var dataBinding: GirlListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        dataBinding = GirlListFragmentBinding.inflate(inflater, container, false).apply {

            setLifecycleOwner(viewLifecycleOwner)
        }
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dataBinding.gankAdView.loadAd(AdRequest.Builder()
                .build())

        val mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        dataBinding.commonRecyclerView.apply {
            animation = null
            layoutManager = mLayoutManager
            addOnScrollListener(OnLoadMoreListener())
            adapter = mAdapter
            setOnTouchListener { _, motionEvent -> mIsRefreshing }
        }

        viewModel.itemData.observe(viewLifecycleOwner, this)

        mAdapter.ontItemClick = viewModel
        viewModel.item.observe(viewLifecycleOwner , Observer {
            ViewBigImgActivity.startViewBigImaActivity(requireContext(), it.url,
                    it.text, true)
        })

        dataBinding.commonSwipeRefresh.setOnRefreshListener {
            firstLoad()
        }

        dataBinding.commonPageErrorLayout.setOnClickListener {

            dataBinding.commonSwipeRefresh.isRefreshing = true
            firstLoad()
        }


        if (!isExcuteUserVisible) {
            isPrepared = true

            fragmentIsVisible = true

            lazyLoad()

            return
        }

        isPrepared = true

        lazyLoad()
    }

    override fun onChanged(list: List<GirlData>?) {

        Logger.d("observe,result ${list}")

        list?.takeIf {

            !it.isEmpty()

        }?.apply {

            dataBinding.commonRecyclerView.visibility = View.VISIBLE
            dataBinding.commonPageErrorLayout.visibility = View.GONE

            mAdapter.submitList(viewModel.list.toList())

            loadFinish = true

            if (dataBinding.commonSwipeRefresh.isRefreshing) {
                dataBinding.commonSwipeRefresh.isRefreshing = false
            }
            mIsRefreshing = false

        } ?: let {
            hasMoreData = false
        }
    }

    private fun loadData() {
        viewModel.loaData(page, pageSize)
    }

    private fun firstLoad() {
        page = 1

        mIsRefreshing = true

        loadData()

        dataBinding.commonPageErrorLayout.visibility = View.GONE
    }

    private fun lazyLoad() {
        if (!isPrepared || !fragmentIsVisible || loadFinish) {
            return
        }

        dataBinding.commonSwipeRefresh.isRefreshing = true


//        getViewModel().errorData.observe(viewLifecycleOwner, Observer {
//            showError()
//        })

        loadData()
    }

    // 懒加载 方法
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        isExcuteUserVisible = true

        if (userVisibleHint) {
            fragmentIsVisible = true
            onVisible()
        } else {
            fragmentIsVisible = false
            onInvisible()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden && !loadFinish) {
            loadData()
            fragmentIsVisible = true
        } else {
            fragmentIsVisible = false
            onInvisible()
        }
    }

    protected fun onInvisible() {}

    protected fun onVisible() {
        lazyLoad()
    }

    private fun showError() {
        dataBinding.commonSwipeRefresh.isRefreshing = false

//        if (getViewModel().list.size > 0) {
//            SnackbarUtil.showMessage(binding.commonRecyclerView,"网络出错啦！")
//
//            binding.commonRecyclerView.visibility = View.VISIBLE
//        } else {
//            binding.commonPageErrorLayout.visibility = View.VISIBLE
//
//            binding.commonRecyclerView.visibility = View.INVISIBLE
//        }
    }

    internal fun OnLoadMoreListener(): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                //这个方法可能返回 -1 导致无法分页
//                val arr = mLayoutManager?.findLastCompletelyVisibleItemPositions(IntArray(2));

                val arr = (dataBinding.commonRecyclerView.layoutManager as? StaggeredGridLayoutManager)
                        ?.findLastVisibleItemPositions(IntArray(2))

                val isBottom = arr!![1] >= mAdapter.getItemCount() - 2

                if (!dataBinding.commonSwipeRefresh.isRefreshing && isBottom) {

                    if (!hasMoreData) return

                    if (!mIsLoadMore) {

                        dataBinding.commonSwipeRefresh.isRefreshing = true

                        page++

                        loadData()

                    } else {
                        mIsLoadMore = false
                    }
                }
            }
        }
    }

}
