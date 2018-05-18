package com.moon.beautygirlkotlin.view_big_img

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.my_collect.model.EventUpdateFavourite
import com.moon.beautygirlkotlin.my_collect.model.MyCollectBody
import com.moon.beautygirlkotlin.realm.RealmUtil
import com.moon.beautygirlkotlin.utils.ImageLoader
import com.moon.beautygirlkotlin.utils.SnackbarUtil
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_gank_view_bigimg.*
import org.greenrobot.eventbus.EventBus

/**
 * author: moon
 * created on: 18/4/28 上午11:43
 * description: 大图片浏览页面
 */
class GankViewBigImgActivity: AppCompatActivity() ,View.OnClickListener{


    var realm: Realm = RealmUtil.getRealm()

    private var url: String = "";
    private var title: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gank_view_bigimg)

        url = intent?.getStringExtra("url")!!

        if (intent?.getStringExtra("title") !=null){
            title= (intent?.getStringExtra("title"))!!
        }



        ImageLoader.load(this,url!!,gank_big_img)

        gank_big_img.enable()

        gank_big_img.setOnClickListener(this)


        toCollect.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        if (v?.id == R.id.toCollect){

            try {

                if (RealmUtil.isCollected(url)){
                    SnackbarUtil.showMessage( v,getString(R.string.collect_has))

                    return
                }

                val body = MyCollectBody()
                body.title = title
                body.url = url
                body.id = url

                RealmUtil.addOneCollect(body)

                SnackbarUtil.showMessage( v,getString(R.string.collect_success))


                EventBus.getDefault().post(EventUpdateFavourite(0))


            }catch (e: Exception){
                e.printStackTrace()
                SnackbarUtil.showMessage( v,getString(R.string.collect_fail))

            }


        }else if (v?.id == R.id.gank_big_img){

            finish()
        }

    }



}