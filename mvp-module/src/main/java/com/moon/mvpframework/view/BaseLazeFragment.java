package com.moon.mvpframework.view;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.moon.mvpframework.factory.PresenterMvpFactory;
import com.moon.mvpframework.factory.PresenterMvpFactoryImpl;
import com.moon.mvpframework.presenter.BaseMvpPresenter;
import com.moon.mvpframework.proxy.BaseMvpProxy;
import com.moon.mvpframework.proxy.PresenterProxyInterface;
import com.moon.mvpframework.view.BaseMvpView;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public abstract class BaseLazeFragment<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends RxFragment implements
        PresenterProxyInterface<V, P> {

    // 懒加载
    protected boolean isVisible;

    protected boolean isPrepared;

    protected boolean loadFinish;

    protected Activity mActivity;

    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract void initViews(View view);

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void loadData();

    protected void onInvisible(){}


    /**
     * 调用onSaveInstanceState时存入Bundle的key
     */
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";
    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V, P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V, P>createFactory(getClass()));

    public BaseLazeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        initViews(view);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
        isPrepared = true;
        lazyLoad();
    }


    private void lazyLoad(){
        if (!isPrepared || !isVisible || loadFinish) {
            return;
        }

        loadData();

    }

    // 懒加载 方法
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(PRESENTER_SAVE_KEY,mProxy.onSaveInstanceState());
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            mProxy.onResume((V) this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }



    /**
     * 可以实现自己PresenterMvpFactory工厂
     *
     * @param presenterFactory PresenterFactory类型
     */
    @Override
    public void setPresenterFactory(PresenterMvpFactory<V, P> presenterFactory) {
        mProxy.setPresenterFactory(presenterFactory);
    }


    /**
     * 获取创建Presenter的工厂
     *
     * @return PresenterMvpFactory类型
     */
    @Override
    public PresenterMvpFactory<V, P> getPresenterFactory() {
        return mProxy.getPresenterFactory();
    }

    /**
     * 获取Presenter
     * @return P
     */
    @Override
    public P getMvpPresenter() {
        return mProxy.getMvpPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProxy.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
