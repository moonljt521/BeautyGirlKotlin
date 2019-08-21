package com.moon.beautygirlkotlin.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.moon.mvpframework.factory.PresenterMvpFactory
import com.moon.mvpframework.factory.PresenterMvpFactoryImpl
import com.moon.mvpframework.presenter.BaseMvpPresenter
import com.moon.mvpframework.proxy.BaseMvpProxy
import com.moon.mvpframework.proxy.PresenterProxyInterface
import com.moon.mvpframework.view.BaseMvpView
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

/**
 * author: moon
 * created on: 2017/5/17 下午8:09
 * description:  fragment mvp 基类
 */

abstract class BaseFragment<V : BaseMvpView<*>, P : BaseMvpPresenter<V>> : AbstractFragment(),
        PresenterProxyInterface<V, P> {

    protected lateinit var mActivity: RxAppCompatActivity

    protected abstract fun getLayoutId(): Int
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private val mProxy = BaseMvpProxy(PresenterMvpFactoryImpl.createFactory<V, P>(javaClass))

    protected abstract fun initData()

    protected abstract fun initViews(view: View?)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = (context as RxAppCompatActivity?)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mProxy.onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(getLayoutId(), container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        try {
            mProxy.onResume(this as V)
        } catch (e: Exception) {
        }

        initData()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putBundle(PRESENTER_SAVE_KEY, mProxy.onSaveInstanceState())
    }

    override fun onDestroy() {
        super.onDestroy()
        mProxy.onDestroy()
    }

    override fun onDestroyView() {

        super.onDestroyView()
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
     *
     * @return P
     */
    override fun getMvpPresenter(): P {
        return mProxy.mvpPresenter
    }

    companion object {


        /**
         * 调用onSaveInstanceState时存入Bundle的key
         */
        private val PRESENTER_SAVE_KEY = "presenter_save_key"
    }

}
