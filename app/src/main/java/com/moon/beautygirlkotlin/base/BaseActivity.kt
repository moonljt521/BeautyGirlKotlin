package com.moon.beautygirlkotlin.base

import android.os.Bundle
import com.moon.beautygirlkotlin.utils.AppManager
import com.trello.rxlifecycle.components.support.RxAppCompatActivity


/**
 *  baseActivity
 */
abstract class BaseActivity : RxAppCompatActivity() {


    abstract fun initViews();
    abstract fun loadData();

    abstract fun getLayoutId() : Int;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getAppManager().addActivity(this)

        setContentView(getLayoutId())

        initViews()

        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getAppManager().finishActivity(this)
    }


}