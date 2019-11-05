package com.moon.beautygirlkotlin.wei1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseBindAdapter
import com.moon.beautygirlkotlin.base.BaseLazyJPFragment
import com.moon.beautygirlkotlin.databinding.FragmentSimpleWeiyiBinding
import com.moon.beautygirlkotlin.utils.InjectorUtil
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.view_big_img.ViewBigImgActivity
import com.moon.beautygirlkotlin.wei1.model.MeiZiTuBody
import com.moon.beautygirlkotlin.wei1.viewmodel.OnlyOneViewModel
import com.moon.beautygirlkotlin.listener.ItemClick
import kotlinx.android.synthetic.main.fragment_simple_douban_meizi.*

/**
 * 妹子图 模块 子fragment
 */
class MeiZiTuSimpleFragment : BaseLazyJPFragment(), ItemClick<MeiZiTuBody> {

    val viewModel: OnlyOneViewModel by lazy {
        ViewModelProviders.of(this, InjectorUtil.getOnlyOneModelFactory()).get(OnlyOneViewModel::class.java)
    }

    var mLayoutManager: StaggeredGridLayoutManager? = null

    lateinit var mAdapter: BaseBindAdapter<ViewDataBinding,MeiZiTuBody>

    var mIsRefreshing: Boolean = false

    var mIsLoadMore = true

    private var page: Int = 1

    private var type: String? = null;

    // 加载数据
    override fun loadData() {

        common_swipe_refresh.post {

            common_swipe_refresh.isRefreshing = true

            mIsRefreshing = true
        }
        loadHttpData()
    }

    companion object {

        fun getInstance(cid: String): MeiZiTuSimpleFragment {
            val fragment = MeiZiTuSimpleFragment();
            val bundle = Bundle()
            bundle.putString("type", cid)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_simple_weiyi, container, false)
        val binding = DataBindingUtil.bind<FragmentSimpleWeiyiBinding>(view)
        binding?.viewModel = viewModel
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        type = arguments?.getString("type")
    }

    override fun initViews(view: View?) {

        mAdapter = BaseBindAdapter(R.layout.item_only_one,viewModel.list)

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

        viewModel._item.observe(this, Observer {
            if (common_swipe_refresh.isRefreshing) {
                common_swipe_refresh?.isRefreshing = false
            }
            showSuccess(it)
        })
    }

    fun showError() {
        common_swipe_refresh.post({ common_swipe_refresh.setRefreshing(false) })

        SnackbarUtil.showMessage(common_recyclerView, getString(R.string.error_message))
    }

    fun showSuccess(list: List<MeiZiTuBody>?) {

        if (page == 1) {

            mAdapter.refreshData(ArrayList(list))

        } else {
            mAdapter.loadMoreData(list!!)
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
        viewModel.getList(type!!, page)
    }

    internal fun OnLoadMoreListener(layoutManager: StaggeredGridLayoutManager): RecyclerView.OnScrollListener {

        return object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val isBottom = mLayoutManager!!.findLastCompletelyVisibleItemPositions(
                        IntArray(2))[1] >= mAdapter.getItemCount() - 6
                if (!common_swipe_refresh.isRefreshing && isBottom) {

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

    override fun onClick(v: View, body: MeiZiTuBody) {
        ViewBigImgActivity.startViewBigImaActivity(mActivity, body.url,
                body.title, true)
    }
}