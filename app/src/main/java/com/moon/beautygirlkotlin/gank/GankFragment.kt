package com.moon.beautygirlkotlin.gank

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.ads.AdRequest
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseJPFragment
import com.moon.beautygirlkotlin.databinding.FragmentJpGankMeiziBinding
import com.moon.beautygirlkotlin.gank.adapter.GankAdapter
import com.moon.beautygirlkotlin.gank.model.GankMeiziBody
import com.moon.beautygirlkotlin.utils.InjectorUtil
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity
import com.moon.beautygirlkotlin.listener.ItemClick
import kotlinx.android.synthetic.main.fragment_jp_gank_meizi.*


/**
 * Gank 妹子模块 fragment
 */
class GankFragment : BaseJPFragment(), ItemClick<GankMeiziBody> {

    private val viewModel by lazy { ViewModelProviders.of(this , InjectorUtil.getGankModelFactory()).get(GankViewModel::class.java) }

    override fun initData() {
        swipe_refresh.post {
            swipe_refresh.isRefreshing = true
            mIsRefreshing = true
        }
        loadHttpData()
    }

    val mLayoutManager: StaggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

    lateinit var mAdapter: GankAdapter

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var pageNum: Int = 15

    companion object {
        fun getInstance(id: Int): GankFragment {
            val fragment = GankFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_jp_gank_meizi, container, false)
        val binding = DataBindingUtil.bind<FragmentJpGankMeiziBinding>(view)
        binding?.viewModel = viewModel
        return view
    }

    override fun initViews(view: View?) {

        mAdapter = GankAdapter(mActivity,viewModel.list)

        mAdapter.refreshData(viewModel.list)

        gank_recyclerView.layoutManager = mLayoutManager

        gank_recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val isBottom = mLayoutManager.findLastCompletelyVisibleItemPositions(IntArray(2))[1] >= mAdapter.getItemCount() - 6
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

        })

        gank_recyclerView.adapter = mAdapter

//        mAdapter.itemListener = this

        gank_recyclerView.setOnTouchListener { _, motionEvent -> mIsRefreshing }

        swipe_refresh.setOnRefreshListener {
            page = 1

            mIsRefreshing = true

            loadHttpData()
        }

        viewModel._item.observe(this, Observer {
            if (swipe_refresh.isRefreshing) {
                swipe_refresh.isRefreshing = false
            }
            showSuccess(it)
        })

        val adRequest = AdRequest.Builder().build()

        gank_adView.loadAd(adRequest)
    }

    /**
     * 加载网络数据：开始[萌妹子数据]的请求
     * 同时开始加重admob 广告
     */
    fun loadHttpData() {

        viewModel.getGankList(pageNum,page)
    }

    fun showError() {

        swipe_refresh.post({ swipe_refresh.setRefreshing(false) })

        SnackbarUtil.showMessage(gank_recyclerView, getString(R.string.error_message))
    }

    fun showSuccess(list: List<GankMeiziBody>?) {


        gank_recyclerView.post {
            if (page == 1) {

                mAdapter.refreshData(ArrayList(list))

            } else {
                mAdapter.loadMoreData(ArrayList(list))
            }

            if (swipe_refresh.isRefreshing) {
                swipe_refresh.isRefreshing = false
            }

            mIsRefreshing = false
        }
    }

    override fun onClick(v: View, body: GankMeiziBody) {
        ViewBigImgActivity.startViewBigImaActivity(mActivity, body?.url,
                body?.desc, true)
    }
}