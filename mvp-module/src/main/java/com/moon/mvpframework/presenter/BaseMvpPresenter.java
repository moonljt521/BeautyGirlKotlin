package com.moon.mvpframework.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.moon.mvpframework.view.BaseMvpView;

/**
 * author: jiangtao.liang
 * date:   On 2019/8/22 18:05
 */
public class BaseMvpPresenter<V extends BaseMvpView> {

    /**
     * V层view
     */
    private V mView;

    /**
     * Presenter被创建后调用
     *
     * @param savedState 被意外销毁后重建后的Bundle
     */
    public void onCreatePersenter(@Nullable Bundle savedState) {

    }


    /**
     * 绑定View
     */
    public void onAttachMvpView(V mvpView) {
        mView = mvpView;
    }

    /**
     * 解除绑定View
     */
    public void onDetachMvpView() {
        mView = null;
    }

    /**
     * Presenter被销毁时调用
     */
    public void onDestroyPersenter() {
    }

    /**
     * 在Presenter意外销毁的时候被调用，它的调用时机和Activity、Fragment、View中的onSaveInstanceState
     * 时机相同
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
    }

    /**
     * 获取V层接口View
     *
     * @return 返回当前MvpView
     */
    public V getMvpView() {
        return mView;
    }
}
