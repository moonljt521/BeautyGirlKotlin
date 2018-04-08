package com.moon.mvpframework.view;


/**
 * author: moon
 * created on:
 * description: 所有View层接口的基类
 */
public interface BaseMvpView<T> {

    void showError();

    void showSuccess(T t);

}
