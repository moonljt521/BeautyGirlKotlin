package com.moon.beautygirlkotlin.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.moon.beautygirlkotlin.MainActivity
import com.moon.beautygirlkotlin.R
import kotlinx.android.synthetic.main.activity_splash.*
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {


    private val ANIMATION_TIME = 2000

    private val SCALE_END = 1.13f

    private var subscribe: Subscription? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        subscribe = Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { aLong -> startAnim() }
    }


    private fun startAnim() {

        val animatorX = ObjectAnimator.ofFloat(splash_image, "scaleX", 1f, SCALE_END)
        val animatorY = ObjectAnimator.ofFloat(splash_image, "scaleY", 1f, SCALE_END)

        val set = AnimatorSet()
        set.setDuration(ANIMATION_TIME.toLong()).play(animatorX).with(animatorY)
        set.start()

        set.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {

                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                this@SplashActivity.finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        })
    }


    override fun onBackPressed() {

        super.onBackPressed()
        if (subscribe != null && !subscribe!!.isUnsubscribed) {
            subscribe!!.unsubscribe()
        }
    }


}