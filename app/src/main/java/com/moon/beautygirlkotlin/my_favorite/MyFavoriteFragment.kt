package com.moon.beautygirlkotlin.my_favorite

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.my_favorite.adapter.MyFavoriteAdapter
import com.moon.beautygirlkotlin.my_favorite.component.MyItemTouchHelperCallBack
import com.moon.beautygirlkotlin.my_favorite.model.EventUpdateFavourite
import com.moon.beautygirlkotlin.my_favorite.viewmodel.FavouriteVieModel
import com.moon.beautygirlkotlin.room.FavoriteBean
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.utils.SpUtil
import kotlinx.android.synthetic.main.fragment_my_favorite.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.text.MessageFormat

/**
 * [我的收藏] 模块 fragment
 */
class MyFavoriteFragment : BaseFragment(), FavouriteItemClick<FavoriteBean> {

    override fun onClick(body: FavoriteBean) {
        viewModel.delItem(body)
    }

    private val rcyDataObserver: RcyDataObserver = RcyDataObserver()

    val viewModel by lazy { ViewModelProviders.of(this).get(FavouriteVieModel::class.java) }

    var mLayoutManager: LinearLayoutManager? = null;

    lateinit var mAdapter: MyFavoriteAdapter

    private var callBack: MyItemTouchHelperCallBack? = null

    private var itemTouchHelper: ItemTouchHelper? = null

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 0

    var hasMoreData = true

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

            queryFavouriteList()
        }
    }

    @Subscribe
    fun refreshFavouriteList(u: EventUpdateFavourite) {
        queryFavouriteList()
    }

    override fun initViews(view: View?) {

        mAdapter = MyFavoriteAdapter(R.layout.item_favourite, viewModel.list)

        mAdapter.listener = this

        callBack = MyItemTouchHelperCallBack(mAdapter)

        mLayoutManager = LinearLayoutManager(mActivity)

        myCollect_recyclerView.layoutManager = mLayoutManager

        // 暂时不支持翻页
        myCollect_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager!!))

        myCollect_recyclerView.adapter = mAdapter

        itemTouchHelper = ItemTouchHelper(callBack!!)

        itemTouchHelper!!.attachToRecyclerView(myCollect_recyclerView)

        myCollect_recyclerView.setOnTouchListener { _, motionEvent -> mIsRefreshing }

        myCollect_swipe_refresh.setOnRefreshListener {
            page = 0

            hasMoreData = true

            mIsRefreshing = true

            queryFavouriteList()
        }
//        mAdapter.registerAdapterDataObserver(rcyDataObserver)

        viewModel.data.observe(this, Observer {
            showSuccess(it)
        })

        viewModel.total.observe(this, Observer {
            if (it > 0) {
                tvFavouriteTotal.text = MessageFormat.format(resources.getString(R.string.total_favourite_size), it)
                tvFavouriteTotal.visibility = View.VISIBLE
            } else {
                tvFavouriteTotal.visibility = View.GONE
            }
        })
        viewModel.getTotalSize()
    }

    /**
     *  查询db
     */
    private fun queryFavouriteList() {
        viewModel.getList(page)
    }

    internal fun OnLoadMoreListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val isBottom = layoutManager.findLastVisibleItemPosition() >= mAdapter.getItemCount() - 1

                if (!myCollect_swipe_refresh.isRefreshing && isBottom) {
                    if (!mIsLoadMore) {

                        if (!hasMoreData) return

                        myCollect_swipe_refresh.isRefreshing = true

                        page++

                        queryFavouriteList()
                    } else {
                        mIsLoadMore = false
                    }
                }
            }
        }
    }

    fun showSuccess(list: List<FavoriteBean>?) {

        list?.let {

            checkEmpty()

            if (it.isEmpty()) {
                hasMoreData = false
                if (myCollect_swipe_refresh.isRefreshing) {
                    myCollect_swipe_refresh.isRefreshing = false
                }
                return
            }

            mAdapter.notifyDataSetChanged()

            if (myCollect_swipe_refresh.isRefreshing) {
                myCollect_swipe_refresh.isRefreshing = false
            }

            mIsRefreshing = false

            if (!SpUtil.tipSwipeDelFavourite()) {
                SnackbarUtil.showMessage(myCollect_recyclerView, getString(R.string.swipe_del_favourite))
            }
        } ?: let {
            hasMoreData = false
            mIsRefreshing = false
        }
    }

    private fun checkEmpty() {
        if (viewModel.list.size == 0) {
            showEmptyView()
        } else {
            hideEmptyView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
//        mAdapter.unregisterAdapterDataObserver(rcyDataObserver)
    }

    inner class RcyDataObserver() : RecyclerView.AdapterDataObserver() {

        override fun onChanged() {
            super.onChanged()
            checkEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            checkEmpty()
        }

        private fun checkEmpty() {
            if (viewModel.list.size == 0) {
                showEmptyView()
            } else {
                hideEmptyView()
            }
        }
    }

    fun showEmptyView() {
        myCollect_empty_text.visibility = View.VISIBLE
    }

    fun hideEmptyView() {
        myCollect_empty_text.visibility = View.GONE
    }
}

