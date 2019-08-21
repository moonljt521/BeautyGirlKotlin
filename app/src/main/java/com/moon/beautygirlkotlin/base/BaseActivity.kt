package com.moon.beautygirlkotlin.base

import android.os.Bundle
import com.moon.beautygirlkotlin.utils.AppManager
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel


/**
 *  baseActivity
 */
abstract class BaseActivity : RxAppCompatActivity(), CoroutineScope by MainScope() {

    abstract fun initViews();
    abstract fun loadData();

    abstract fun getLayoutId() : Int;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)

        setContentView(getLayoutId())

        initViews()

        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
        AppManager.instance.finishActivity(this)
    }
}