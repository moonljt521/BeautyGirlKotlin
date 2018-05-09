package com.moon.beautygirlkotlin.taofemale

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewTreeObserver
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.gank.GankViewBigImgActivity
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.taofemale.adapter.TaoFemaleAdapter
import com.moon.beautygirlkotlin.taofemale.model.Contentlist
import com.moon.beautygirlkotlin.taofemale.presenter.TaoFemalePresenter
import com.moon.beautygirlkotlin.taofemale.view.ITaoFemaleView
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.mvpframework.factory.CreatePresenter
import com.moon.mvpframework.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_gank_meizi.*

/**
 * [淘女郎] 模块 fragment
 */
@CreatePresenter(TaoFemalePresenter::class)
class TaoFemaleFragment : BaseFragment<ITaoFemaleView, TaoFemalePresenter>(), ITaoFemaleView, ViewItemListener {


    val mLayoutManager: LinearLayoutManager = LinearLayoutManager(mActivity)

    lateinit var mAdapter: TaoFemaleAdapter

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageNum: Int = 15

    var imageIndex: Int = 0

    companion object {

        fun getInstance(id: Int): TaoFemaleFragment {
            var fragment = TaoFemaleFragment();
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

        mAdapter = TaoFemaleAdapter()

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
     * 加载网络数据：开始[淘女郎数据]的请求
     */
    fun loadHttpData() {
        mvpPresenter?.getGankList(mActivity!!, page)
    }

    internal fun OnLoadMoreListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(rv: RecyclerView?, dx: Int, dy: Int) {

                val isBottom = layoutManager.findLastVisibleItemPosition() >= mAdapter.getItemCount() - 2

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

    override fun showSuccess(list: List<Contentlist>?) {

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
        intent.putExtra("url",mAdapter?.list?.get(position)?.avatarUrl)

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