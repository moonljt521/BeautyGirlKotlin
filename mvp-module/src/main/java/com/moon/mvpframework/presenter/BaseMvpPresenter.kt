package com.moon.mvpframework.presenter

import android.os.Bundle

import com.moon.mvpframework.view.BaseMvpView

/**
 * author: moon
 * created on:
 * description:  presenter 基类
 */
open class BaseMvpPresenter<V : BaseMvpView<V>> {

    /**
     * V层view
     */
    /**
     * 获取V层接口View
     *
     * @return 返回当前MvpView
     */
    var mvpView: V? = null
        private set

    /**
     * Presenter被创建后调用
     *
     * @param savedState 被意外销毁后重建后的Bundle
     */
    fun onCreatePersenter(savedState: Bundle?) {

    }


    /**
     * 绑定View
     */
    fun onAttachMvpView(mvpView: V) {
        this.mvpView = mvpView
    }

    /**
     * 解除绑定View
     */
    fun onDetachMvpView() {
        mvpView = null
    }

    /**
     * Presenter被销毁时调用
     */
    fun onDestroyPersenter() {}

    /**
     * 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     * 时机相同
     *
     * @param outState
     */
    fun onSaveInstanceState(outState: Bundle) {}
}
