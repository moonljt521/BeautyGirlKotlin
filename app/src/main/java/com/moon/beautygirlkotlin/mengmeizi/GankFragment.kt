package com.moon.beautygirlkotlin.mengmeizi

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.SharedElementCallback
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewTreeObserver
import com.facebook.stetho.common.LogUtil
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.mengmeizi.model.GankMeiziAdapter
import com.moon.beautygirlkotlin.mengmeizi.model.GankMeiziBody
import com.moon.beautygirlkotlin.mengmeizi.presenter.GankMeiziPresenter
import com.moon.beautygirlkotlin.mengmeizi.view.IGankMeiziView
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.mvpframework.view.BaseFragment
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.verticalLayout


/**
 * 萌妹子模块 fragment
 */
class GankFragment : BaseFragment<IGankMeiziView, GankMeiziPresenter>(), IGankMeiziView, ViewItemListener {


    lateinit var rootView: View

    lateinit var recyclerView: RecyclerView

    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    lateinit var mLayoutManager: StaggeredGridLayoutManager

    lateinit var mAdapter: GankMeiziAdapter

    val ID_RECYCLERVIEW: Int = 1
    val ID_SWIPE_REFRESHLAYOUT: Int = 2

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

        return 0
    }

    override fun getFragmentView(): View {
        rootView = UI {

            verticalLayout {

                swipeRefreshLayout {

                    id = ID_SWIPE_REFRESHLAYOUT

                    setColorSchemeResources(R.color.colorPrimary)

                    recyclerView {

                        id = ID_RECYCLERVIEW

                        mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

                        layoutManager = mLayoutManager

                    }.lparams(width = matchParent, height = matchParent)

                }.lparams(width = matchParent, height = matchParent)

            }

        }.view



        return rootView
    }

    /**
     * 初始化
     */
    override fun initData() {

        mSwipeRefreshLayout.post {

            mSwipeRefreshLayout.isRefreshing = true

            mIsRefreshing = true
        }

        loadHttpData()
    }

    override fun initViews(view: View?) {
        recyclerView = view?.findViewById(ID_RECYCLERVIEW)!!
        mSwipeRefreshLayout = view?.findViewById(ID_SWIPE_REFRESHLAYOUT)

        mAdapter = GankMeiziAdapter()

        recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager))

        recyclerView.adapter = mAdapter

        mAdapter.itemListener = this

        recyclerView.setOnTouchListener { view, motionEvent -> mIsRefreshing }

        mSwipeRefreshLayout.setOnRefreshListener {
            page = 1

            mIsRefreshing = true

            loadHttpData()
        }

//        RxBus.getInstance().toObserverable(Intent::class.java)
//                .compose(this.bindToLifecycle())
//                .subscribe({ intent ->
//
//                    imageIndex = intent.getIntExtra("index", -1)
//                    scrollIndex()
//                    finishTask()
//                }, { throwable ->
//
//                    LogUtil.all(throwable.getMessage())
//                })

//        setEnterSharedElementCallback(object : SharedElementCallback() {
//
//            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
//
//                super.onMapSharedElements(names, sharedElements)
//                var newTransitionName = mAdapter.list?.get(imageIndex)?.url
//                var newSharedView = recyclerView?.findViewWithTag(newTransitionName)
//                if (newSharedView != null) {
//                    names!!.clear()
//                    names.add(newTransitionName)
//                    sharedElements!!.clear()
//                    sharedElements[newTransitionName] = newSharedView
//                }
//            }
//        })

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
                if (!mSwipeRefreshLayout.isRefreshing && isBottom) {
                    if (!mIsLoadMore) {

                        mSwipeRefreshLayout.isRefreshing = true

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

        mSwipeRefreshLayout.post({ mSwipeRefreshLayout.setRefreshing(false) })

        SnackbarUtil.showMessage(recyclerView, getString(R.string.error_message))
    }

    override fun showSuccess(list: List<GankMeiziBody>?) {

//        if (page * pageNum - pageNum - 1 > 0) {
//            mAdapter.notifyItemRangeChanged(page * pageNum - pageNum - 1, pageNum)
//        } else {
//            mAdapter.notifyDataSetChanged()
//        }

        if (page == 1){

            mAdapter.refreshData(list!!)

        }else{
            mAdapter.loadMoreData(list!!)
        }

        if (mSwipeRefreshLayout.isRefreshing) {
            mSwipeRefreshLayout.isRefreshing = false
        }

        mIsRefreshing = false
    }

    override fun itemClick(v: View, position: Int) {
        //            val intent = mActivity.luanch(activity, position)
//
//            if (Build.VERSION.SDK_INT >= 22) {
//                startActivity(intent,
//                        ActivityOptions.makeSceneTransitionAnimation(activity,
//                                holder.getParentView().findViewById(R.id.item_img),
//                                mAdapter.list.get(position).url).toBundle())
//            } else {
//                startActivity(intent)
//            }

    }

    fun scrollIndex() {

        if (imageIndex != -1) {
            recyclerView.scrollToPosition(imageIndex)
            recyclerView.getViewTreeObserver()
                    .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {

                        override fun onPreDraw(): Boolean {

                            recyclerView.getViewTreeObserver().removeOnPreDrawListener(this)
                            recyclerView.requestLayout()
                            return true
                        }
                    })
        }
    }


}