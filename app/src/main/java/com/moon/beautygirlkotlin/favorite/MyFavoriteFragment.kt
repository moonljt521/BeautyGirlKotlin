package com.moon.beautygirlkotlin.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.base.BaseFragment
import com.moon.beautygirlkotlin.databinding.FragmentMyFavoriteBinding
import com.moon.beautygirlkotlin.common.livdedatabus.LiveDataBusKt
import com.moon.beautygirlkotlin.favorite.adapter.MyFavoriteAdapter
import com.moon.beautygirlkotlin.favorite.component.MyItemTouchHelperCallBack
import com.moon.beautygirlkotlin.favorite.viewmodel.FavouriteVieModel
import com.moon.beautygirlkotlin.common.room.FavoriteBean
import com.moon.beautygirlkotlin.common.room.FavoriteBeanOther
import com.moon.beautygirlkotlin.common.utils.SnackbarUtil
import com.moon.beautygirlkotlin.common.utils.SpUtil
import dagger.hilt.android.AndroidEntryPoint
import java.text.MessageFormat

/**
 * [我的收藏] 模块 fragment
 */
@AndroidEntryPoint
class MyFavoriteFragment : BaseFragment(), FavouriteItemClick<Any> {

    override fun onClick(body: Any) {
        if (body is FavoriteBean) viewModel.delItem(body)
    }

    private lateinit var databinding: FragmentMyFavoriteBinding

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

        fun getInstance(id: Int) = MyFavoriteFragment().apply {
            arguments = Bundle().apply { putInt("id", id) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = FragmentMyFavoriteBinding.inflate(inflater, container, false)
        databinding.apply {
            viewModel = this@MyFavoriteFragment.viewModel
            setLifecycleOwner(viewLifecycleOwner)
        }
        return databinding.root
    }

    /**
     * 初始化
     */
    override fun initData() {

        databinding.myCollectSwipeRefresh.post {

            databinding.myCollectSwipeRefresh.isRefreshing = true

            mIsRefreshing = true

            queryFavouriteList()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LiveDataBusKt.get()?.with("favourite",String::class.java)
                ?.observe(this, Observer {
                    queryFavouriteList()
                })
    }

    override fun initViews(view: View?) {

        mAdapter = MyFavoriteAdapter(viewModel.list , viewType = object : BaseBindAdapter.ViewTypeCallBack {

            override fun createViewType(position: Int): Int {
                val t = viewModel.list[position]
                if (t is FavoriteBean) return R.layout.item_favourite
                if (t is FavoriteBeanOther) return R.layout.item_favourite_other
                return 0
            }
        })

        mAdapter.listener = this

        callBack = MyItemTouchHelperCallBack(mAdapter)

        mLayoutManager = LinearLayoutManager(requireContext())

        databinding.myCollectRecyclerView.apply {

            layoutManager = mLayoutManager

            addOnScrollListener(OnLoadMoreListener(mLayoutManager!!))

            adapter = mAdapter

            setOnTouchListener { _, motionEvent -> mIsRefreshing }
        }

        itemTouchHelper = ItemTouchHelper(callBack!!)

        itemTouchHelper!!.attachToRecyclerView(databinding.myCollectRecyclerView)


        databinding.myCollectSwipeRefresh.setOnRefreshListener {
            page = 0

            hasMoreData = true

            mIsRefreshing = true

            queryFavouriteList()
        }

        viewModel.data.observe(this, Observer {
            showSuccess(it)
            checkEmpty()
        })

        viewModel.total.observe(this, Observer {
            if (it > 0) {
                databinding.tvFavouriteTotal.text = MessageFormat.format(resources.getString(R.string.total_favourite_size), it)
                databinding.tvFavouriteTotal.visibility = View.VISIBLE
            } else {
                databinding.tvFavouriteTotal.visibility = View.GONE
            }
            checkEmpty()
        })
    }

    /**
     *  查询db
     */
    private fun queryFavouriteList() {
        if (viewModel.total.value!! < (10 * page)){
            viewModel.getList(page - 1)
        }else{
            viewModel.getList(page)
        }
        viewModel.getTotalSize()
    }

    internal fun OnLoadMoreListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val isBottom = layoutManager.findLastVisibleItemPosition() >= mAdapter.getItemCount() - 1

                if (!databinding.myCollectSwipeRefresh.isRefreshing && isBottom) {
                    if (!mIsLoadMore) {

                        if (!hasMoreData) return

                        databinding.myCollectSwipeRefresh.isRefreshing = true

                        page++

                        queryFavouriteList()
                    } else {
                        mIsLoadMore = false
                    }
                }
            }
        }
    }

    fun showSuccess(list: List<Any>?) {

        list?.let {

            if (it.isEmpty()) {
                hasMoreData = false
                if (databinding.myCollectSwipeRefresh.isRefreshing) {
                    databinding.myCollectSwipeRefresh.isRefreshing = false
                }
                return
            }

            mAdapter.notifyDataSetChanged()

            if (databinding.myCollectSwipeRefresh.isRefreshing) {
                databinding.myCollectSwipeRefresh.isRefreshing = false
            }

            mIsRefreshing = false

            if (!SpUtil.tipSwipeDelFavourite()) {
                SnackbarUtil.showMessage(databinding.myCollectRecyclerView, getString(R.string.swipe_del_favourite))
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

    fun showEmptyView() {
        databinding.myCollectEmptyText.visibility = View.VISIBLE
    }

    fun hideEmptyView() {
        databinding.myCollectEmptyText.visibility = View.GONE
    }
}

