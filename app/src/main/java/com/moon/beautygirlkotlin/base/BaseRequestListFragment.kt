package com.moon.beautygirlkotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.moon.beautygirlkotlin.databinding.FragmentBaseRequestBinding
import com.moon.beautygirlkotlin.utils.Logger
import com.moon.beautygirlkotlin.utils.SnackbarUtil

/**
 * author: moon
 * created on: 2019/11/15 下午8:09
 * description:
 */
abstract class BaseRequestListFragment<T> : BaseFragment(), Observer<List<T>> {

    private lateinit var binding: FragmentBaseRequestBinding

    val logName = "BeautyGirlLog"

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageSize: Int = 10

    var hasMoreData = true

    var mAdapter: CommonBindAdapter<ViewDataBinding, T>? = null

    var mLayoutManager: StaggeredGridLayoutManager? = null

    // 懒加载
    protected var fragmentIsVisible: Boolean = false

    protected var isExcuteUserVisible: Boolean = false

    protected var isPrepared: Boolean = false

    protected var loadFinish: Boolean = false

    protected abstract fun loadData()

    protected abstract fun getViewModel(): BaseViewModel<T>

    protected abstract fun getItemLayoutId(): Int

    protected fun onInvisible() {}

    protected fun onVisible() {
        lazyLoad()
    }

    override fun initData() {}

    override fun onChanged(list: List<T>?) {

        list?.takeIf {

            !it.isEmpty()

        }?.apply {

            binding.commonRecyclerView.visibility = View.VISIBLE
            binding.commonPageErrorLayout.visibility = View.GONE

            if (page == 1) {

                mAdapter?.notifyItemChanged(0, getViewModel().list.size)

            } else {
                mAdapter?.notifyItemRangeInserted(getViewModel().list.size - list.size, getViewModel().list.size)
            }

            loadFinish = true

            if (binding.commonSwipeRefresh.isRefreshing) {
                binding.commonSwipeRefresh.isRefreshing = false
            }
            mIsRefreshing = false

        } ?: let {
            hasMoreData = false
        }
    }

    private fun lazyLoad() {
        if (!isPrepared || !fragmentIsVisible || loadFinish) {
            return
        }

        binding.commonSwipeRefresh.isRefreshing = true

        getViewModel().data.observe(viewLifecycleOwner, this)

        getViewModel().errorData.observe(viewLifecycleOwner, Observer {
            showError()
        })

        loadData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!isExcuteUserVisible) {
            isPrepared = true

            fragmentIsVisible = true

            lazyLoad()

            return
        }

        isPrepared = true

        lazyLoad()

        val adRequest = AdRequest.Builder()
                .build()

        binding.gankAdView.loadAd(adRequest)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentBaseRequestBinding.inflate(inflater, container, false)
        return binding.apply {
            viewModel = getViewModel()
            setLifecycleOwner(viewLifecycleOwner)
        }.root
    }

    override fun initViews(view: View?) {

        mAdapter = CommonBindAdapter(getItemLayoutId(), getViewModel().list)

        mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        binding.commonRecyclerView.apply {
            animation = null
            layoutManager = mLayoutManager
            addOnScrollListener(OnLoadMoreListener())
            adapter = mAdapter
            setOnTouchListener { _, motionEvent -> mIsRefreshing }
        }

        binding.commonSwipeRefresh.setOnRefreshListener {
            firstLoad()
        }

        binding.commonPageErrorLayout.setOnClickListener {

            binding.commonSwipeRefresh.isRefreshing = true

            firstLoad()
        }
    }

    private fun firstLoad() {
        page = 1

        mIsRefreshing = true

        loadData()

        binding.commonPageErrorLayout.visibility = View.GONE
    }

    private fun showError() {
        binding.commonSwipeRefresh.isRefreshing = false

        if (getViewModel().list.size > 0) {
            SnackbarUtil.showMessage(binding.commonRecyclerView,"网络出错啦！")

            binding.commonRecyclerView.visibility = View.VISIBLE
        } else {
            binding.commonPageErrorLayout.visibility = View.VISIBLE

            binding.commonRecyclerView.visibility = View.INVISIBLE
        }
    }

    // 懒加载 方法
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        Logger.i(log = logName, value = javaClass.name + "........... setUserVisibleHint = " + isVisibleToUser)

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

        Logger.i(log = logName, value = javaClass.name + "........... onHiddenChanged = " + !hidden)

        if (!hidden && !loadFinish) {
            loadData()
            fragmentIsVisible = true
        } else {
            fragmentIsVisible = false
            onInvisible()
        }
    }

    internal fun OnLoadMoreListener(): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                //这个方法可能返回 -1 导致无法分页
//                val arr = mLayoutManager?.findLastCompletelyVisibleItemPositions(IntArray(2));

                val arr = mLayoutManager?.findLastVisibleItemPositions(IntArray(2))

                val isBottom = arr!![1] >= mAdapter?.getItemCount()!! - 2

                if (!binding.commonSwipeRefresh.isRefreshing && isBottom) {

                    if (!hasMoreData) return

                    if (!mIsLoadMore) {

                        binding.commonSwipeRefresh.isRefreshing = true

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
