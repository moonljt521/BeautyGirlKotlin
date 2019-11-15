package com.moon.beautygirlkotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.databinding.FragmentBaseRequestBinding
import com.moon.beautygirlkotlin.listener.ItemClick
import com.moon.beautygirlkotlin.utils.Logger
import kotlinx.android.synthetic.main.fragment_base_request.*

/**
 * author: moon
 * created on: 2019/11/15 下午8:09
 * description:
 */
abstract class BaseRequestListFragment<T> : BaseFragment(), ItemClick<T>, Observer<List<T>> {

    val logName = "BeautyGirlLog"

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageSize: Int = 10

    var hasMoreData = true

    lateinit var mAdapter: BaseBindAdapter<ViewDataBinding, T>

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

            mAdapter.notifyDataSetChanged()

            loadFinish = true

            if (common_swipe_refresh.isRefreshing) {
                common_swipe_refresh.isRefreshing = false
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

        common_swipe_refresh.isRefreshing = true

        getViewModel().data.observe(viewLifecycleOwner,this)

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
    }

    override fun getLayoutId(): Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_base_request, container, false)
        val binding = DataBindingUtil.bind<FragmentBaseRequestBinding>(view)
        binding?.viewModel = getViewModel()
        return view
    }

    override fun initViews(view: View?) {

        mAdapter = BaseBindAdapter(getItemLayoutId(), getViewModel().list)

        mAdapter.ontItemClick = this

        mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        common_recyclerView.layoutManager = mLayoutManager

        common_recyclerView.addOnScrollListener(OnLoadMoreListener())

        common_recyclerView.adapter = mAdapter

        common_recyclerView.setOnTouchListener { _, motionEvent -> mIsRefreshing }

        common_swipe_refresh.setOnRefreshListener {
            page = 1

            mIsRefreshing = true

            loadData()
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

                val isBottom = arr!![1] >= mAdapter.getItemCount() - 2

                if (!common_swipe_refresh.isRefreshing && isBottom) {

                    if (!hasMoreData) return

                    if (!mIsLoadMore) {

                        common_swipe_refresh.isRefreshing = true

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
