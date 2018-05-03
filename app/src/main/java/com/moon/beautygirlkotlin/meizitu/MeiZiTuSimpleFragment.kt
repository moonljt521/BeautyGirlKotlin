package com.moon.beautygirlkotlin.meizitu

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.gank.GankViewBigImgActivity
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.meizitu.adapter.MeiZiTuAdapter
import com.moon.beautygirlkotlin.meizitu.model.MeiZiTuBody
import com.moon.beautygirlkotlin.meizitu.presenter.MeiZiTuPresenter
import com.moon.beautygirlkotlin.meizitu.view.IMeiZiTuView
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.mvpframework.factory.CreatePresenter
import com.moon.mvpframework.view.BaseLazeFragment
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.fragment_simple_douban_meizi.*

/**
 * 妹子图 模块 子fragment
 */
@CreatePresenter(MeiZiTuPresenter::class)
class MeiZiTuSimpleFragment : BaseLazeFragment<IMeiZiTuView, MeiZiTuPresenter>(), IMeiZiTuView, ViewItemListener {

    val mLayoutManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    lateinit var mAdapter: MeiZiTuAdapter

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

        fun getInstance(cid: String): MeiZiTuSimpleFragment {
            var fragment = MeiZiTuSimpleFragment();
            var bundle = Bundle()
            bundle.putString("type", cid)

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

        mAdapter = MeiZiTuAdapter()

        douban_recyclerView.layoutManager = mLayoutManager

        douban_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager))

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

    override fun showSuccess(list: List<MeiZiTuBody>?) {
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
     * 加载网络数据：开始[萌妹子数据]的请求
     */
    fun loadHttpData() {
        mvpPresenter.getMeizitu(mActivity as RxAppCompatActivity,arguments.getString("type"),page)
    }

    internal fun OnLoadMoreListener(layoutManager: StaggeredGridLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {

                val isBottom = mLayoutManager.findLastCompletelyVisibleItemPositions(
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
        val intent = Intent(mActivity, GankViewBigImgActivity::class.java)
        intent.putExtra("url",mAdapter?.list?.get(position)?.imageurl)

        mActivity.startActivity(intent)
    }


}