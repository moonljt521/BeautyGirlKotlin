package com.moon.mvpframework.view

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

import com.moon.mvpframework.factory.PresenterMvpFactoryImpl
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.moon.mvpframework.proxy.BaseMvpProxy
import com.moon.mvpframework.proxy.PresenterProxyInterface



/**
 * author: moon
 * created on:
 * description: common包下  activity 基类  无toolbar
 */
abstract class BaseMvpActivity<V : BaseMvpView<*>, P : BaseMvpPresenter<V>> : AppCompatActivity(), PresenterProxyInterface<V, P> {

    private val onbackListener: RegisterOnBackListener? = null


    protected var isDestroy: Boolean = false

    protected var isFirstLoad: Boolean = false

    var isLandscape: Boolean = false

    /**
     * 返回一个layoutId
     */
    protected abstract val layoutId: Int

    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private val mProxy = BaseMvpProxy(PresenterMvpFactoryImpl.createFactory<V, P>(javaClass))

    protected abstract fun init()

    protected abstract fun initViews()

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY))
        }
        mProxy.onResume(this as V)

        isDestroy = false
        // 强制竖屏
        if (isLandscape) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        }
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }
        setContentView(layoutId)
        initViews()
        init()

    }

    public override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    public override fun onStop() {
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onDestroy() {
        super.onDestroy()

        mProxy.onDestroy()

        isDestroy = true


    }

    override fun onBackPressed() {
        if (onbackListener != null) {
            onbackListener.onBack()
            return
        }
        finish()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (currentFocus != null && currentFocus!!.windowToken != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val v = currentFocus
                if (v != null) {
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    interface RegisterOnBackListener {
        fun onBack()
    }

    companion object {
        private val PRESENTER_SAVE_KEY = "presenter_save_key"
    }


}
