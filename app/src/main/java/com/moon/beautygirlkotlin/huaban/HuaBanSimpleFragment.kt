package com.moon.beautygirlkotlin.huaban

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseLazeFragment
import com.moon.beautygirlkotlin.huaban.model.HuaBanAdapter
import com.moon.beautygirlkotlin.huaban.model.HuaBanBody
import com.moon.beautygirlkotlin.huaban.presenter.HuaBanPresenter
import com.moon.beautygirlkotlin.huaban.view.IHuaBanView
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity
import com.moon.mvpframework.factory.CreatePresenter
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.fragment_simple_douban_meizi.*

/**
 * 花瓣模块 子fragment
 */
@CreatePresenter(HuaBanPresenter::class)
class HuaBanSimpleFragment : BaseLazeFragment<IHuaBanView, HuaBanPresenter>(), IHuaBanView, ViewItemListener {

    var mLayoutManager: StaggeredGridLayoutManager? = null

    lateinit var mAdapter: HuaBanAdapter

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageNum: Int = 15

    var imageIndex: Int = 0


    // 加载数据
    override fun loadData() {

        douban_swipe_refresh.post {

            douban_swipe_refresh.isRefreshing = true

            mIsRefreshing = true
        }

        loadHttpData()
    }


    companion object {

        fun getInstance(id: Int): HuaBanSimpleFragment {
            val fragment = HuaBanSimpleFragment();
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
    override fun init() {


    }

    override fun initViews(view: View?) {

        mAdapter = HuaBanAdapter()

        mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        douban_recyclerView.layoutManager = mLayoutManager

        douban_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager!!))

        douban_recyclerView.adapter = mAdapter

        mAdapter.itemListener = this

        douban_recyclerView.setOnTouchListener { view, motionEvent -> mIsRefreshing }

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

    override fun showSuccess(list: List<HuaBanBody>?) {
        if (page == 1) {

            mAdapter.refreshData(list!!)

        } else {
            mAdapter.loadMoreData(list!!)
        }

        if (douban_swipe_refresh.isRefreshing) {
            douban_swipe_refresh.isRefreshing = false
        }

        loadFinish = true

        mIsRefreshing = false
    }

    /**
     * 加载网络数据：开始[花瓣妹子数据]的请求
     */
    fun loadHttpData() {
        mvpPresenter.getHuaBan(mActivity as RxAppCompatActivity,
                pageNum,
                page,
                arguments.getInt("id"))
    }

    internal fun OnLoadMoreListener(layoutManager: StaggeredGridLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {

                val isBottom = mLayoutManager!!.findLastCompletelyVisibleItemPositions(
                        IntArray(2))[1] >= mAdapter.getItemCount() - 6

                if (!douban_swipe_refresh.isRefreshing && isBottom) {
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
        ViewBigImgActivity.startViewBigImaActivity(mActivity,mAdapter.list?.get(position)?.url,
                mAdapter.list?.get(position)?.title,false)
    }


}