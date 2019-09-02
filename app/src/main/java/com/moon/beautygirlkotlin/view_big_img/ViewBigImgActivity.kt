package com.moon.beautygirlkotlin.view_big_img

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.my_favorite.model.EventUpdateFavourite
import com.moon.beautygirlkotlin.my_favorite.model.MyFavoriteBody
import com.moon.beautygirlkotlin.realm.RealmUtil
import com.moon.beautygirlkotlin.utils.ImageLoader
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_gank_view_bigimg.*
import org.greenrobot.eventbus.EventBus
import android.widget.TextView
import android.view.LayoutInflater
import android.support.design.widget.BottomSheetDialog
import android.widget.Button


/**
 * author: moon
 * created on: 18/4/28 上午11:43
 * description: 大图片浏览页面
 */
class ViewBigImgActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

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

    var realm: Realm = RealmUtil.getRealm()

    private var url: String = "";
    private var title: String = "";

    private var showCollectIcon: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gank_view_bigimg)

        url = intent?.getStringExtra("url")!!

        title = (intent?.getStringExtra("title"))!!

        showCollectIcon = (intent?.getBooleanExtra("showCollectIcon", true))!!

        collect_btn.visibility = if (showCollectIcon) View.VISIBLE else View.GONE

        ImageLoader.load(this, url, gank_big_img)

        gank_big_img.enable()

        gank_big_img.setOnClickListener(this)

        gank_big_img.setOnLongClickListener(this)

        collect_btn.setOnClickListener(this)

        val collectIcon: Int = if (RealmUtil.isCollected(url)) R.drawable.collected else R.drawable.uncollected

        toCollect.setImageResource(collectIcon)
    }

    override fun onClick(v: View?) {

        if (v?.id == R.id.collect_btn) {

            try {

                if (RealmUtil.isCollected(url)) {
                    SnackbarUtil.showMessage(v, getString(R.string.collect_has))
                    return
                }

                val body = MyFavoriteBody()
                body.title = title
                body.url = url
                body.id = url

                RealmUtil.addOneCollect(body)

                SnackbarUtil.showMessage(v, getString(R.string.collect_success))

                toCollect.setImageResource(R.drawable.collected)

                EventBus.getDefault().post(EventUpdateFavourite(0))

            } catch (e: Exception) {
                e.printStackTrace()
                SnackbarUtil.showMessage(v, getString(R.string.collect_fail))
            }

        } else if (v?.id == R.id.gank_big_img) {
            finish()
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