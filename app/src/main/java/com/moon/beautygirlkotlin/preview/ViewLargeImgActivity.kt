package com.moon.beautygirlkotlin.preview

import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.room.Room
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseActivity
import com.moon.beautygirlkotlin.common.livdedatabus.LiveDataBusKt
import com.moon.beautygirlkotlin.common.room.BeautyGirlDatabase
import com.moon.beautygirlkotlin.common.room.FavoriteBean
import com.moon.beautygirlkotlin.common.utils.ImageLoader
import com.moon.beautygirlkotlin.common.utils.SnackbarUtil
import com.moon.beautygirlkotlin.common.widget.RoundedBackgroundSpan
import com.moon.beautygirlkotlin.databinding.ActivityGankViewBigimgBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * author: moon
 * created on: 18/4/28 上午11:43
 * description: 大图片浏览页面
 */
@AndroidEntryPoint
class ViewLargeImgActivity : BaseActivity(), View.OnClickListener, View.OnLongClickListener {

    private lateinit var binding: ActivityGankViewBigimgBinding

    @Inject
    lateinit var db: BeautyGirlDatabase


    private var url: String = "";
    private var title: String = "";

    private var showCollectIcon: Boolean = true

    private var titleSpan: RoundedBackgroundSpan? = null

    override fun initViews() {
        titleSpan = RoundedBackgroundSpan(this, R.color.red)

        url = intent?.getStringExtra("url")!!

        title = (intent?.getStringExtra("title"))!!

        showCollectIcon = (intent?.getBooleanExtra("showCollectIcon", true))!!

        binding.collectBtn.let {
            it.visibility = if (showCollectIcon) View.VISIBLE else View.GONE
            it.setOnClickListener(this)
        }

        ImageLoader.load(this, url, binding.gankBigImg)

        binding.gankBigImg.let {
            it.setOnClickListener(this)
            it.setOnLongClickListener(this)
        }

        binding.toCollect.setImageResource(R.drawable.uncollected)
    }

    override fun loadData() {
        launch {
            val qb = withContext(Dispatchers.IO) {
                db.favouriteDao().getFavouriteByUrl(url)
            }
            qb?.let {
                binding.toCollect.setImageResource(R.drawable.collected)
                binding.collectBtn.isEnabled = false
            }
        }

        titleSpan?.setSpanText("精选")

        val spannableString = SpannableString(SpannableStringBuilder().also {
            it.append("   ")
            it.append(title)
        }.toString())

        spannableString.setSpan(titleSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvViewBigImageTitle.text = spannableString
    }

    override fun getLayoutId(): Int {
        binding = ActivityGankViewBigimgBinding.inflate(layoutInflater)
        setContentView(binding.root)
        return R.layout.activity_gank_view_bigimg
    }

    override fun onLongClick(p0: View?): Boolean {

        showDialog()

        return true
    }

    private fun showDialog() {
        val dialog = BottomSheetDialog(this@ViewLargeImgActivity)
        val dialogView = LayoutInflater.from(this@ViewLargeImgActivity)
                .inflate(R.layout.dialog_bottom, null)
        val tvTakePhoto = dialogView.findViewById(R.id.tv_save_img) as Button
        val tvCancel = dialogView.findViewById(R.id.tv_cancel) as Button

        tvTakePhoto.setOnClickListener {

            dialog.dismiss()
        }

        tvCancel.setOnClickListener { dialog.dismiss() }
        dialog.setContentView(dialogView)
        dialog.show()
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.collect_btn -> {
                launch {
                    val result = withContext(Dispatchers.IO) {
                        db.favouriteDao().getFavouriteByUrl(url)
                    }

                    result?.let {
                        SnackbarUtil.showMessage(v, getString(R.string.collect_has))
                    } ?: let {
                        withContext(Dispatchers.IO) {
                            db.favouriteDao().insertFavourite(
                                    FavoriteBean(title = title,url = url,createTime = System.currentTimeMillis()))
                        }.let {
                            SnackbarUtil.showMessage(v, getString(R.string.collect_success))

                            binding.toCollect.setImageResource(R.drawable.collected)

                            LiveDataBusKt.get()?.with("favourite")?.postValue("")
                        }
                    }
                }
            }

            R.id.gank_big_img -> {
                finish()
            }
        }
    }

    companion object {

        fun startViewBigImaActivity(context: Context, url: String?, title: String?, showCollectIcon: Boolean): Unit {
            context.startActivity(with(Intent(context, ViewLargeImgActivity::class.java)){
                putExtra("url", url)
                putExtra("title", title)
                putExtra("showCollectIcon", showCollectIcon)
            })
        }
    }
}