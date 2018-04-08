package com.moon.mvpframework.factory;


import com.moon.mvpframework.presenter.BaseMvpPresenter;
import com.moon.mvpframework.view.BaseMvpView;

public interface PresenterMvpFactory<V extends BaseMvpView,P extends BaseMvpPresenter<V>> {

    /**
     * 创建Presenter的接口方法
     * @return 需要创建的Presenter
     */
    P createMvpPresenter();
}
