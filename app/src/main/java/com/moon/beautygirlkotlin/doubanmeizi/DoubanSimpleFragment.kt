package com.moon.beautygirlkotlin.doubanmeizi

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseLazeFragment
import com.moon.beautygirlkotlin.doubanmeizi.model.DouBanAdapter
import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.doubanmeizi.presenter.DoubanPresenter
import com.moon.beautygirlkotlin.doubanmeizi.view.IDouBanView
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.view_big_img.GankViewBigImgActivity
import com.moon.mvpframework.factory.CreatePresenter
import kotlinx.android.synthetic.main.fragment_simple_douban_meizi.*


/**
 * 豆瓣模块 子fragment
 */
@CreatePresenter(DoubanPresenter::class)
class DoubanSimpleFragment : BaseLazeFragment<IDouBanView, DoubanPresenter>(),IDouBanView, ViewItemListener {

    var mLayoutManager: StaggeredGridLayoutManager? = null

    lateinit var mAdapter: DouBanAdapter

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageNum: Int = 15

    var hasMoreData = true

    // 加载数据
    override fun loadData() {

        douban_swipe_refresh.post {

            douban_swipe_refresh.isRefreshing = true

            mIsRefreshing = true
        }

        loadHttpData()
    }


    companion object {

        fun getInstance(id: Int): DoubanSimpleFragment {
            val fragment = DoubanSimpleFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_simple_douban_meizi
    }


    /**
     * 初始化
     */
    override fun init() {}

    override fun initViews(view: View?) {

        mAdapter = DouBanAdapter()

        mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        douban_recyclerView.layoutManager = mLayoutManager

        douban_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager!!))

        douban_recyclerView.adapter = mAdapter

        mAdapter.itemListener = this

        douban_recyclerView.setOnTouchListener { _, motionEvent -> mIsRefreshing }

        douban_swipe_refresh.setOnRefreshListener {
            page = 1

            mIsRefreshing = true

            loadHttpData()
        }
    }

    override fun showError() {
        douban_swipe_refresh.post({ douban_swipe_refresh.setRefreshing(false) })

        SnackbarUtil.showMessage(douban_recyclerView, getString(R.string.error_message))
    }

    override fun showSuccess(list: List<DoubanMeiziBody>?) {
        if (list?.size == 0){
            SnackbarUtil.showMessage(douban_recyclerView, getString(R.string.nodata_message))
            hasMoreData = false
        }else {
            if (page == 1) {
                mAdapter.refreshData(list!!)

            } else {
                mAdapter.loadMoreData(list!!)
            }
        }

        if (douban_swipe_refresh.isRefreshing) {
            douban_swipe_refresh.isRefreshing = false
        }

        loadFinish = true

        mIsRefreshing = false
    }

    /**
     * 加载网络数据：开始[萌妹子数据]的请求
     */
    fun loadHttpData() {
        mvpPresenter.getDouBanMeiZiData(coroutineContext,arguments.getInt("id"),page,1)
    }

    internal fun OnLoadMoreListener(layoutManager: StaggeredGridLayoutManager?): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {

                val arr = mLayoutManager?.findLastCompletelyVisibleItemPositions(IntArray(2));

                val isBottom = arr!![1] >= mAdapter.getItemCount() - 6

                if (!douban_swipe_refresh.isRefreshing && isBottom) {
                    if (!hasMoreData){
                        return
                    }
                    if (!mIsLoadMore) {

                        douban_swipe_refresh.isRefreshing = true

                        page++

                        loadHttpData()
                    } else {
                        mIsLoadMore = false
                    }
                }
            }
        }
    }

    override fun itemClick(v: View, position: Int) {
        GankViewBigImgActivity.startViewBigImaActivity(mActivity,mAdapter.list?.get(position)?.url,
                mAdapter.list?.get(position)?.title,true)
    }


}