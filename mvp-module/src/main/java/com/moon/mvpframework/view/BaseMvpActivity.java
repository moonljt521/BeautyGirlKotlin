package com.moon.mvpframework.view;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.moon.mvpframework.factory.PresenterMvpFactoryImpl;
import com.moon.mvpframework.presenter.BaseMvpPresenter;
import com.moon.mvpframework.proxy.BaseMvpProxy;
import com.moon.mvpframework.proxy.PresenterProxyInterface;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;


/**
 * author: moon
 * created on:
 * description: common包下  activity 基类  无toolbar
 */
public abstract class BaseMvpActivity<V extends BaseMvpView, P extends BaseMvpPresenter<V>> extends
        RxAppCompatActivity implements PresenterProxyInterface<V,P> {

    private RegisterOnBackListener onbackListener;


    protected boolean isDestroy;

    protected boolean isFirstLoad;

    public boolean isLandscape;

    /**
     * 返回一个layoutId
     */
    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract void initViews();

    /**
     * 创建被代理对象,传入默认Presenter的工厂
     */
    private BaseMvpProxy<V,P> mProxy = new BaseMvpProxy<>(PresenterMvpFactoryImpl.<V,P>createFactory(getClass()));
    private static final String PRESENTER_SAVE_KEY = "presenter_save_key";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mProxy.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_SAVE_KEY));
        }
        mProxy.onResume((V) this);

        isDestroy = false;
        // 强制竖屏
        if (isLandscape){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(getLayoutId());
        initViews();
        init();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();

        mProxy.onDestroy();

        isDestroy = true;


    }

    @Override
    public void onBackPressed() {
        if (onbackListener != null) {
            onbackListener.onBack();
            return;
        }
        finish();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                View v = getCurrentFocus();
                if (v != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public interface RegisterOnBackListener {
        void onBack();
    }


}
