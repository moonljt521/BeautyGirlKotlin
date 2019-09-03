package com.moon.beautygirlkotlin.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import com.moon.beautygirlkotlin.MainActivity
import com.moon.beautygirlkotlin.R
import com.moon.beautygirlkotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity() {

    private val ANIMATION_TIME = 2000

    private val SCALE_END = 1.13f

    override fun initViews() {

    }

    override fun loadData() {
        launch(coroutineContext) {
            delay(1000)
            startAnim()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
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
        cancel()
    }
}