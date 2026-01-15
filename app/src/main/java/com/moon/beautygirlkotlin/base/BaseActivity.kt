package com.moon.beautygirlkotlin.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.moon.beautygirlkotlin.common.utils.AppManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel


/**
 *  baseActivity
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    abstract fun initViews();
    abstract fun loadData();

    abstract fun getLayoutId() : Int;

    // 可选：子类可以重写此方法来提供自定义的 content view
    open fun getContentView(): View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)

        // 优先使用 getContentView()，如果返回 null 则使用 getLayoutId()
        val contentView = getContentView()
        if (contentView != null) {
            setContentView(contentView)
        } else {
            setContentView(getLayoutId())
        }

        initViews()

        loadData()
    }

    override fun onDestroy() {
        cancel()
        AppManager.instance.finishActivity(this)
        super.onDestroy()
    }
}