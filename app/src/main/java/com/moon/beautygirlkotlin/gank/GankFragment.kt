package com.moon.beautygirlkotlin.gank

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewTreeObserver
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.gank.model.GankMeiziAdapter
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.beautygirlkotlin.gank.presenter.GankMeiziPresenter
import com.moon.beautygirlkotlin.gank.view.IGankMeiziView
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.mvpframework.factory.CreatePresenter
import com.moon.mvpframework.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_gank_meizi.*
import org.jetbrains.anko.startActivityForResult


/**
 * 萌妹子模块 fragment
 */
@CreatePresenter(GankMeiziPresenter::class)
class GankFragment : BaseFragment<IGankMeiziView, GankMeiziPresenter>(), IGankMeiziView, ViewItemListener {


    val mLayoutManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
//    val mLayoutManager: GridLayoutManager = GridLayoutManager(mActivity,2)

    lateinit var mAdapter: GankMeiziAdapter

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageNum: Int = 15

    var imageIndex: Int = 0

    companion object {

        fun getInstance(id: Int): GankFragment {
            var fragment = GankFragment();
            var bundle = Bundle()
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

        mAdapter = GankMeiziAdapter()

        gank_recyclerView.layoutManager = mLayoutManager

        gank_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager))

        gank_recyclerView.adapter = mAdapter

        mAdapter.itemListener = this

        gank_recyclerView.setOnTouchListener { view, motionEvent -> mIsRefreshing }

        swipe_refresh.setOnRefreshListener {
            page = 1

            mIsRefreshing = true

            loadHttpData()
        }
    }

    /**
     * 加载网络数据：开始[萌妹子数据]的请求
     */
    fun loadHttpData() {
        mvpPresenter?.getGankList(mActivity, pageNum, page)
    }

    internal fun OnLoadMoreListener(layoutManager: StaggeredGridLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {

                val isBottom = mLayoutManager.findLastCompletelyVisibleItemPositions(
                        IntArray(2))[1] >= mAdapter.getItemCount() - 6
                if (!swipe_refresh.isRefreshing && isBottom) {
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

    override fun showSuccess(list: List<GankMeiziBody>?) {

        if (page == 1) {

            mAdapter.refreshData(list!!)

        } else {
            mAdapter.loadMoreData(list!!)
        }

        if (swipe_refresh.isRefreshing) {
            swipe_refresh.isRefreshing = false
        }

        mIsRefreshing = false
    }

    private val EXTRA_INDEX = "extra_index"


    override fun itemClick(v: View, position: Int) {

//        val mIntent = Intent(activity, GankMeiziPageActivity::class.java)
//        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        mIntent.putExtra(EXTRA_INDEX, position)
//
//        if (Build.VERSION.SDK_INT >= 22) {
//            startActivity(mIntent,
//                    ActivityOptions.makeSceneTransitionAnimation(activity,
//                            holder.getParentView().findViewById(R.id.item_img),
//                            mAdapter.list?.get(position)?.url).toBundle())
//        } else {
//            startActivity(mIntent)
//        }
        val intent = Intent(mActivity, GankViewBigImgActivity::class.java)
        intent.putExtra("url",mAdapter?.list?.get(position)?.url)

        mActivity.startActivity(intent)


    }

    fun scrollIndex() {

        if (imageIndex != -1) {
            gank_recyclerView.scrollToPosition(imageIndex)
            gank_recyclerView.getViewTreeObserver()
                    .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {

                        override fun onPreDraw(): Boolean {

                            gank_recyclerView.getViewTreeObserver().removeOnPreDrawListener(this)
                            gank_recyclerView.requestLayout()
                            return true
                        }
                    })
        }
    }


//    override fun getMvpPresenter(): GankMeiziPresenter {
//        return GankMeiziPresenter()
//    }


}