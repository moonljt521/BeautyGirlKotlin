package com.moon.beautygirlkotlin.admeizi

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.moon.beautygirlkotlin.R
import com.moon.mvpframework.view.BaseFragment
import com.moon.mvpframework.view.BaseIdFragment
import kotlinx.android.synthetic.main.fragment_ad_meizi.*

/**
 * 广告 妹子模块 fragment
 */
class AdMeiziFragment : BaseIdFragment<Nothing, Nothing>() {


    companion object {

        fun getInstance(id: Int): AdMeiziFragment {
            var fragment = AdMeiziFragment();
            var bundle = Bundle()
            bundle.putInt("id", id)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun getLayoutId(): Int {

        return R.layout.fragment_ad_meizi
    }


    /**
     * 初始化
     */
    override fun initData() {


    }

    override fun initViews(view: View?) {
        val adRequest = AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build()
        my_adView.loadAd(adRequest)


        my_adView.adListener = object :AdListener(){
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.i("moon","onAdLoaded...")
            }

            override fun onAdFailedToLoad(errorCode : Int) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        }

    }



}