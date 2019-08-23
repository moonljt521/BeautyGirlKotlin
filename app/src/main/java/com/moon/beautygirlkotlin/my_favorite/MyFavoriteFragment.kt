package com.moon.beautygirlkotlin.my_favorite

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.listener.ViewItemListener
import com.moon.beautygirlkotlin.my_favorite.adapter.MyFavoriteAdapter
import com.moon.beautygirlkotlin.my_favorite.component.MyItemTouchHelperCallBack
import com.moon.beautygirlkotlin.my_favorite.model.EventUpdateFavourite
import com.moon.beautygirlkotlin.my_favorite.model.MyFavoriteBody
import com.moon.beautygirlkotlin.my_favorite.presenter.MyFavoritePresenter
import com.moon.beautygirlkotlin.my_favorite.view.IMyFavoriteView
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.utils.SpUtil
import com.moon.beautygirlkotlin.view_big_img.GankViewBigImgActivity
import com.moon.mvpframework.factory.CreatePresenter
import kotlinx.android.synthetic.main.fragment_my_favorite.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * [我的收藏] 模块 fragment
 */
@CreatePresenter(MyFavoritePresenter::class)
class MyFavoriteFragment : BaseFragment<IMyFavoriteView, MyFavoritePresenter>(), IMyFavoriteView, ViewItemListener {

    var mLayoutManager: LinearLayoutManager? = null ;

    lateinit var mAdapter: MyFavoriteAdapter

    private var callBack: MyItemTouchHelperCallBack? = null

    private var itemTouchHelper: ItemTouchHelper? = null

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageNum: Int = 15

    companion object {

        fun getInstance(id: Int): MyFavoriteFragment {
            val fragment = MyFavoriteFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_my_favorite
    }

    /**
     * 初始化
     */
    override fun initData() {

        myCollect_swipe_refresh.post {

            myCollect_swipe_refresh.isRefreshing = true

            mIsRefreshing = true

            queryMyCollect4db()

        }
    }

    @Subscribe
    fun refreshFavouriteList(u: EventUpdateFavourite){
        queryMyCollect4db()
    }


    override fun initViews(view: View?) {

        mAdapter = MyFavoriteAdapter()

        callBack = MyItemTouchHelperCallBack(mAdapter)

        mLayoutManager = LinearLayoutManager(mActivity)

        myCollect_recyclerView.layoutManager = mLayoutManager

        // 暂时不支持翻页
//        myCollect_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager))

        myCollect_recyclerView.adapter = mAdapter

        itemTouchHelper = ItemTouchHelper(callBack)

        itemTouchHelper!!.attachToRecyclerView(myCollect_recyclerView)

        mAdapter.itemListener = this

        myCollect_recyclerView.setOnTouchListener { _, motionEvent -> mIsRefreshing }

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

        SnackbarUtil.showMessage(myCollect_recyclerView, getString(R.string.image_error))
    }

    override fun itemClick(v: View, position: Int) {
        GankViewBigImgActivity.startViewBigImaActivity(mActivity,mAdapter.list?.get(position)?.url,
                mAdapter.list?.get(position)?.title,false)
    }

    override fun showSuccess(list : List<MyFavoriteBody>?) {

        if (page == 1) {

            mAdapter.refreshData(list!!)

        } else {
            mAdapter.loadMoreData(list!!)
        }

        if (myCollect_swipe_refresh.isRefreshing) {
            myCollect_swipe_refresh.isRefreshing = false
        }

        mIsRefreshing = false

        if (!SpUtil.tipSwipeDelFavourite()){
            SnackbarUtil.showMessage(myCollect_recyclerView, getString(R.string.swipe_del_favourite))
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}