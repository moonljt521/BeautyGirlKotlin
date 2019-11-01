package com.moon.beautygirlkotlin.doubanmeizi

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseLazyJPFragment
import com.moon.beautygirlkotlin.databinding.FragmentSimpleDoubanMeiziBinding
import com.moon.beautygirlkotlin.doubanmeizi.model.DouBanAdapter
import com.moon.beautygirlkotlin.doubanmeizi.model.DoubanMeiziBody
import com.moon.beautygirlkotlin.doubanmeizi.viewmodel.DoubanViewModel
import com.moon.beautygirlkotlin.listener.ItemClick
import com.moon.beautygirlkotlin.utils.InjectorUtil
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity
import kotlinx.android.synthetic.main.fragment_simple_douban_meizi.*


/**
 * 豆瓣模块 子fragment
 */
class DoubanSimpleFragment : BaseLazyJPFragment() , ItemClick<DoubanMeiziBody> {

    override fun onClick(view: View, body: DoubanMeiziBody) {
        ViewBigImgActivity.startViewBigImaActivity(mActivity,body.url,
                body.title,true)
    }

    val viewModel by lazy {
        ViewModelProviders.of(this, InjectorUtil.getDoubanModelFactory()).get(DoubanViewModel::class.java)
    }

    var mLayoutManager: StaggeredGridLayoutManager? = null

    lateinit var mAdapter: DouBanAdapter

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var hasMoreData = true

    // 加载数据
    override fun loadData() {

        common_swipe_refresh.post {

            common_swipe_refresh.isRefreshing = true

            mIsRefreshing = true
        }

        loadHttpData()
    }

    companion object {
        fun getInstance(id: Int): DoubanSimpleFragment {
            val fragment = DoubanSimpleFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_simple_douban_meizi, container, false)
        val binding = DataBindingUtil.bind<FragmentSimpleDoubanMeiziBinding>(view)
        binding?.viewModel = viewModel
        return view
    }

    override fun initViews(view: View?) {

        mAdapter = DouBanAdapter(viewModel.list)

        mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        common_recyclerView.layoutManager = mLayoutManager

        common_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager!!))

        common_recyclerView.adapter = mAdapter

        common_recyclerView.setOnTouchListener { _, motionEvent -> mIsRefreshing }

        common_swipe_refresh.setOnRefreshListener {
            page = 1

            mIsRefreshing = true

            loadHttpData()
        }

        mAdapter.itemListener = this

        viewModel.data.observe(this, Observer {
            showSuccess(it)
        })
    }

    fun showError() {
        common_swipe_refresh.post({ common_swipe_refresh.setRefreshing(false) })

        SnackbarUtil.showMessage(common_recyclerView, getString(R.string.error_message))
    }

    fun showSuccess(list: List<DoubanMeiziBody>?) {
        if (list?.size == 0){
            hasMoreData = false
        }else {
            if (page == 1) {
                mAdapter.refreshData(ArrayList(list))

            } else {
                mAdapter.loadMoreData(list!!)
            }
        }

        if (common_swipe_refresh.isRefreshing) {
            common_swipe_refresh.isRefreshing = false
        }

        loadFinish = true

        mIsRefreshing = false
    }

    /**
     * 加载网络数据：开始[萌妹子数据]的请求
     */
    fun loadHttpData() {
        viewModel.getList(arguments!!.getInt("id"),page,1)
    }

    internal fun OnLoadMoreListener(layoutManager: StaggeredGridLayoutManager?): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val arr = mLayoutManager?.findLastCompletelyVisibleItemPositions(IntArray(2));

                val isBottom = arr!![1] >= mAdapter.getItemCount() - 6

                if (!common_swipe_refresh.isRefreshing && isBottom) {
                    if (!hasMoreData){
                        return
                    }
                    if (!mIsLoadMore) {

                        common_swipe_refresh.isRefreshing = true

                        page++

                        loadHttpData()
                    } else {
                        mIsLoadMore = false
                    }
                }
            }
        }
    }

}