package com.moon.beautygirlkotlin.my_collect

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.gank.GankViewBigImgActivity
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.my_collect.adapter.MyCollectAdapter
import com.moon.beautygirlkotlin.my_collect.model.MyCollectBody
import com.moon.beautygirlkotlin.my_collect.presenter.MyCollectPresenter
import com.moon.beautygirlkotlin.my_collect.view.IMyCollectView
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.mvpframework.factory.CreatePresenter
import com.moon.mvpframework.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_my_collect.*

/**
 * [我的收藏] 模块 fragment
 */
@CreatePresenter(MyCollectPresenter::class)
class MyCollectFragment : BaseFragment<IMyCollectView, MyCollectPresenter>(), IMyCollectView, ViewItemListener {


    val mLayoutManager: LinearLayoutManager = LinearLayoutManager(mActivity)

    lateinit var mAdapter: MyCollectAdapter

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageNum: Int = 15

    companion object {

        fun getInstance(id: Int): MyCollectFragment {
            var fragment = MyCollectFragment();
            var bundle = Bundle()
            bundle.putInt("id", id)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun getLayoutId(): Int {

        return R.layout.fragment_my_collect
    }

    /**
     * 初始化
     */
    override fun initData() {

        myCollect_swipe_refresh.post {

            myCollect_swipe_refresh.isRefreshing = true

            mIsRefreshing = true
        }

        queryMyCollect4db()
    }

    override fun initViews(view: View?) {

        mAdapter = MyCollectAdapter()

        myCollect_recyclerView.layoutManager = mLayoutManager


        // 暂时不支持翻页
//        myCollect_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager))

        myCollect_recyclerView.adapter = mAdapter

        mAdapter.itemListener = this

        myCollect_recyclerView.setOnTouchListener { view, motionEvent -> mIsRefreshing }

        myCollect_swipe_refresh.setOnRefreshListener {
            page = 1

            mIsRefreshing = true

            queryMyCollect4db()
        }
    }

    /**
     *  查询db
     */
    fun queryMyCollect4db() {
        mvpPresenter?.getMyCollectList(mActivity)
    }

    internal fun OnLoadMoreListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {

                val isBottom = layoutManager.findLastVisibleItemPosition() >= mAdapter.getItemCount() - 2

                if (!myCollect_swipe_refresh.isRefreshing && isBottom) {
                    if (!mIsLoadMore) {

                        myCollect_swipe_refresh.isRefreshing = true

                        page++

                        queryMyCollect4db()
                    } else {
                        mIsLoadMore = false
                    }
                }
            }
        }
    }

    override fun showError() {

        myCollect_swipe_refresh.post({ myCollect_swipe_refresh.setRefreshing(false) })

        SnackbarUtil.showMessage(myCollect_recyclerView, getString(R.string.error_message))
    }



    private val EXTRA_INDEX = "extra_index"


    override fun itemClick(v: View, position: Int) {

        val intent = Intent(mActivity, GankViewBigImgActivity::class.java)
        intent.putExtra("url",mAdapter?.list?.get(position)?.url)
        intent.putExtra("title",mAdapter?.list?.get(position)?.title)

        mActivity.startActivity(intent)
    }

    override fun showSuccess(list : List<MyCollectBody>?) {

        if (page == 1) {

            mAdapter.refreshData(list!!)

        } else {
            mAdapter.loadMoreData(list!!)
        }

        if (myCollect_swipe_refresh.isRefreshing) {
            myCollect_swipe_refresh.isRefreshing = false
        }

        mIsRefreshing = false
    }

}