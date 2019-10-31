package com.moon.beautygirlkotlin.base


import android.app.Fragment
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

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
abstract class BaseLazeFragment<V : BaseMvpView<*>, P : BaseMvpPresenter<V>> : AbstractFragment(), PresenterProxyInterface<V, P> {

    // 懒加载
    protected var fragmentIsVisible: Boolean = false

    protected var isPrepared: Boolean = false

    protected var loadFinish: Boolean = false

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

}
