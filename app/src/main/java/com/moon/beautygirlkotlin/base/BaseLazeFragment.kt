package com.moon.beautygirlkotlin.base


import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import com.moon.mvpframework.factory.PresenterMvpFactory
import com.moon.mvpframework.factory.PresenterMvpFactoryImpl
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.moon.mvpframework.proxy.BaseMvpProxy
import com.moon.mvpframework.proxy.PresenterProxyInterface
import com.moon.mvpframework.view.BaseMvpView

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
abstract class BaseLazeFragment<V : BaseMvpView<*>, P : BaseMvpPresenter<V>> : AbstractFragment(), PresenterProxyInterface<V, P> {

    // 懒加载
    protected var fragmentIsVisible: Boolean = false

    protected var isPrepared: Boolean = false

    protected var loadFinish: Boolean = false

    protected lateinit var mActivity: AppCompatActivity

    protected abstract fun getLayoutId(): Int
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private val mProxy = BaseMvpProxy(PresenterMvpFactoryImpl.createFactory<V, P>(javaClass))

    protected abstract fun init()

    protected abstract fun initViews(view: View?)

    protected fun onVisible() {
        lazyLoad()
    }

    protected abstract fun loadData()

    protected fun onInvisible() {}

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = (context as AppCompatActivity?)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        isPrepared = true
        lazyLoad()
    }


    private fun lazyLoad() {
        if (!isPrepared || !fragmentIsVisible || loadFinish) {
            return
        }

        loadData()

    }

    // 懒加载 方法
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        if (userVisibleHint) {
            fragmentIsVisible = true
            onVisible()
        } else {
            fragmentIsVisible = false
            onInvisible()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState!!.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState())
    }


    override fun onResume() {
        super.onResume()
        try {
            mProxy.onResume(this as V)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
    }


    /**
     * 可以实现自己PresenterMvpFactory工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    override fun setPresenterFactory(presenterFactory: PresenterMvpFactory<V, P>) {
        mProxy.presenterFactory = presenterFactory
    }


    /**
     * 获取创建Presenter的工厂
     *
     * @return PresenterMvpFactory类型
     */
    override fun getPresenterFactory(): PresenterMvpFactory<V, P> {
        return mProxy.presenterFactory
    }

    /**
     * 获取Presenter
     * @return P
     */
    override fun getMvpPresenter(): P {
        return mProxy.mvpPresenter
    }

    override fun onDestroy() {
        super.onDestroy()
        mProxy.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        /**
         * 调用onSaveInstanceState时存入Bundle的key
         */
        private val PRESENTER_SAVE_KEY = "presenter_save_key"
    }

}
