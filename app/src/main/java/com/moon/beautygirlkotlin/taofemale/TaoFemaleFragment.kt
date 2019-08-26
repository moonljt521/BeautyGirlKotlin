package com.moon.beautygirlkotlin.taofemale

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.taofemale.adapter.TaoFemaleAdapter
import com.moon.beautygirlkotlin.taofemale.presenter.TaoFemalePresenter
import com.moon.beautygirlkotlin.taofemale.view.ITaoFemaleView
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.view_big_img.GankViewBigImgActivity
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.mvpframework.factory.CreatePresenter
import kotlinx.android.synthetic.main.fragment_gank_meizi.*

/**
 * [美图录] 模块 fragment
 */
@CreatePresenter(TaoFemalePresenter::class)
class TaoFemaleFragment : BaseFragment<ITaoFemaleView, TaoFemalePresenter>(), ITaoFemaleView, ViewItemListener {

    var mLayoutManager: LinearLayoutManager? = null ;

    lateinit var mAdapter: TaoFemaleAdapter

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 2

    var pageNum: Int = 15

    var hasMoreData = true

    companion object {

        fun getInstance(id: Int): TaoFemaleFragment {
            val fragment = TaoFemaleFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_gank_meizi
    }

    /**
     * 初始化
     */
    override fun initData() {

        swipe_refresh.post {

            swipe_refresh.isRefreshing = true

            mIsRefreshing = true
        }

        loadHttpData()
    }

    override fun initViews(view: View?) {

        mAdapter = TaoFemaleAdapter()

        mLayoutManager = LinearLayoutManager(mActivity)

        gank_recyclerView.layoutManager = mLayoutManager

        gank_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager!!))

        gank_recyclerView.adapter = mAdapter

        mAdapter.itemListener = this

        gank_recyclerView.setOnTouchListener { view, motionEvent -> mIsRefreshing }

        swipe_refresh.setOnRefreshListener {
            page = 2

            mIsRefreshing = true

            loadHttpData()
        }
    }

    /**
     * 加载网络数据：开始[淘女郎数据]的请求
     */
    fun loadHttpData() {
        mvpPresenter.getTaoFemaleList(page)
    }

    internal fun OnLoadMoreListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {

                val isBottom = layoutManager.findLastVisibleItemPosition() >= mAdapter.getItemCount() - 4

                if (!swipe_refresh.isRefreshing && isBottom) {

                    if (!hasMoreData){
                        return
                    }

                    if (!mIsLoadMore) {

                        swipe_refresh.isRefreshing = true

                        page++

                        loadHttpData()
                    } else {
                        mIsLoadMore = false
                    }
                }
            }
        }
    }

    override fun showError() {

        swipe_refresh.post({ swipe_refresh.setRefreshing(false) })

        SnackbarUtil.showMessage(gank_recyclerView, getString(R.string.error_message))
    }

    override fun showSuccess(list: List<MeiZiTuBody>?) {

        if (list?.size == 0){
            SnackbarUtil.showMessage(gank_recyclerView, getString(R.string.nodata_message))
            hasMoreData = false
        }else{
            hasMoreData = true

            if (page == 1) {

                mAdapter.refreshData(list!!)

            } else {
                mAdapter.loadMoreData(list!!)
            }
        }

        if (swipe_refresh.isRefreshing) {
            swipe_refresh.isRefreshing = false
        }

        mIsRefreshing = false
    }

    private val EXTRA_INDEX = "extra_index"


    override fun itemClick(v: View, position: Int) {
        GankViewBigImgActivity.startViewBigImaActivity(mActivity,mAdapter.list?.get(position)?.url,
                mAdapter.list?.get(position)?.title,true)

    }
}