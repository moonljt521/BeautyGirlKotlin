package com.moon.mvpframework.view

/**
 * author: moon
 * created on:
 * description: 所有View层接口的基类
 */
interface BaseMvpView<T> {

    fun showError()

    fun showSuccess(t: T?)

}
