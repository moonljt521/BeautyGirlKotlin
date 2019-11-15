package com.moon.beautygirlkotlin.view_big_img

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.moon.beautygirlkotlin.BeautyGirlKotlinApp
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseActivity
import com.moon.beautygirlkotlin.my_favorite.model.EventUpdateFavourite
import com.moon.beautygirlkotlin.room.BeautyGirlDatabase
import com.moon.beautygirlkotlin.room.FavoriteBean
import com.moon.beautygirlkotlin.utils.ImageLoader
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import com.moon.beautygirlkotlin.widget.RoundedBackgroundSpan
import kotlinx.android.synthetic.main.activity_gank_view_bigimg.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus


/**
 * author: moon
 * created on: 18/4/28 上午11:43
 * description: 大图片浏览页面
 */
class ViewBigImgActivity : BaseActivity(), View.OnClickListener, View.OnLongClickListener {


    private var db: BeautyGirlDatabase;

    init {
        db = Room.databaseBuilder(
                BeautyGirlKotlinApp.application,
                BeautyGirlDatabase::class.java, "beauty_girl.db")
                .build()
    }

    private var url: String = "";
    private var title: String = "";

    private var showCollectIcon: Boolean = true

    private var titleSpan: RoundedBackgroundSpan? = null

    override fun initViews() {
        titleSpan = RoundedBackgroundSpan(this, R.color.red)

        url = intent?.getStringExtra("url")!!

        title = (intent?.getStringExtra("title"))!!

        showCollectIcon = (intent?.getBooleanExtra("showCollectIcon", true))!!

        collect_btn.visibility = if (showCollectIcon) View.VISIBLE else View.GONE

        ImageLoader.load(this, url, gank_big_img)

        gank_big_img.enable()

        gank_big_img.setOnClickListener(this)

        gank_big_img.setOnLongClickListener(this)

        collect_btn.setOnClickListener(this)

        toCollect.setImageResource(R.drawable.uncollected)
    }

    override fun loadData() {
        launch {
            val qb = withContext(Dispatchers.IO) {
                db.favouriteDao().getFavouriteByUrl(url)
            }
            qb?.let {
                toCollect.setImageResource(R.drawable.collected)
                collect_btn.isEnabled = false
            }
        }

        titleSpan?.setSpanText("精选")
        val stringBuilder = SpannableStringBuilder()
        stringBuilder.append("   ")
        stringBuilder.append(title)
        val spannableString = SpannableString(stringBuilder.toString())
        spannableString.setSpan(titleSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tvViewBigImageTitle.text = spannableString
    }

    override fun getLayoutId(): Int = R.layout.activity_gank_view_bigimg

    override fun onLongClick(p0: View?): Boolean {

        showDialog()

        return true
    }

    fun showDialog() {
        val dialog = BottomSheetDialog(this@ViewBigImgActivity)
        val dialogView = LayoutInflater.from(this@ViewBigImgActivity)
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

                            toCollect.setImageResource(R.drawable.collected)

                            EventBus.getDefault().post(EventUpdateFavourite(0))
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
            val intent = Intent(context, ViewBigImgActivity::class.java)
            intent.putExtra("url", url)
            intent.putExtra("title", title)
            intent.putExtra("showCollectIcon", showCollectIcon)
            context.startActivity(intent)
        }
    }
}