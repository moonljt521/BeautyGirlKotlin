package com.moon.beautygirlkotlin.youtumeiku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.base.BaseJPFragment
import com.moon.beautygirlkotlin.databinding.FragmentSimpleOnlyoneBinding
import com.moon.beautygirlkotlin.listener.ItemClick
import com.moon.beautygirlkotlin.utils.InjectorUtil
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.wei1.viewmodel.OnlyOneViewModel
import kotlinx.android.synthetic.main.fragment_simple_douban_meizi.*

/**
 * [优图美库] 模块 fragment
 */
class YouTuMeikuFragment : BaseJPFragment(), ItemClick<MeiZiTuBody> {

    private val viewModel by lazy { ViewModelProviders.of(this, InjectorUtil.getOnlyOneModelFactory()).get(OnlyOneViewModel::class.java) }

    var mLayoutManager: LinearLayoutManager? = null;

    lateinit var mAdapter: BaseBindAdapter<ViewDataBinding,MeiZiTuBody>

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    var page: Int = 1

    var hasMoreData = true

    companion object {
        fun getInstance(id: Int): YouTuMeikuFragment {
            val fragment = YouTuMeikuFragment();
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    /**
     * 初始化
     */
    override fun initData() {
        common_swipe_refresh.post {
            common_swipe_refresh.isRefreshing = true
            mIsRefreshing = true
        }
        loadHttpData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_simple_onlyone, container, false)
        val binding = DataBindingUtil.bind<FragmentSimpleOnlyoneBinding>(view)
        binding?.viewModel = viewModel
        return view
    }

    override fun initViews(view: View?) {

        mAdapter = BaseBindAdapter(R.layout.item_only_one, viewModel.list)

        mAdapter.ontItemClick = this

        mLayoutManager = LinearLayoutManager(mActivity)

        common_recyclerView.layoutManager = mLayoutManager

        common_recyclerView.addOnScrollListener(OnLoadMoreListener(mLayoutManager!!))

        common_recyclerView.adapter = mAdapter

        common_recyclerView.setOnTouchListener { view, motionEvent -> mIsRefreshing }

        common_swipe_refresh.setOnRefreshListener {
            page = 1

            mIsRefreshing = true

            loadHttpData()
        }

        viewModel.youtuData.observe(this, Observer {

            showSuccess(it)
        })
    }

    /**
     * 加载网络数据
     */
    fun loadHttpData() {
        viewModel.getTaoFemaleList(page)
    }

    internal fun OnLoadMoreListener(layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val isBottom = layoutManager.findLastVisibleItemPosition() >= mAdapter.getItemCount() - 4

                if (!common_swipe_refresh.isRefreshing && isBottom) {

                    if (!hasMoreData) {
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

    fun showError() {

        common_swipe_refresh.post({ common_swipe_refresh.setRefreshing(false) })

        SnackbarUtil.showMessage(common_recyclerView, getString(R.string.error_message))
    }

    fun showSuccess(t: List<MeiZiTuBody>?) {

        if (t?.size == 0) {

            hasMoreData = false
        } else {
            hasMoreData = true

            if (page == 1) {

                mAdapter.refreshData(ArrayList(t))

            } else {
                mAdapter.loadMoreData(t!!)
            }
        }

        if (common_swipe_refresh.isRefreshing) {
            common_swipe_refresh.isRefreshing = false
        }
        mIsRefreshing = false
    }

    override fun onClick(view: View, body: MeiZiTuBody) {
        ViewBigImgActivity.startViewBigImaActivity(mActivity, body.url,
                body.title, true)
    }
}