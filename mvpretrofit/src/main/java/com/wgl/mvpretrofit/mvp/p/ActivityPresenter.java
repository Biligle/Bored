package com.wgl.mvpretrofit.mvp.p;

import android.os.Bundle;
import android.view.Window;

import com.wgl.mvpretrofit.mvp.v.IDelegate;
import zhy.autolayout.AutoLayout;
import zhy.autolayout.AutoLayoutActivity;

/**
 * Presenter层的实现基类
 * @param <T>
 */
public abstract class ActivityPresenter<T extends IDelegate> extends AutoLayoutActivity {
    protected T baseView;

    public ActivityPresenter() {
        try {
            baseView = getViewClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseView.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(baseView.getRootView());
        //在Activity初始化AutoLayout
        AutoLayout.getInstance().auto(this);
        baseView.initWidget();
        setInit();
        setListener();
    }
    /**
     * 普通设置（包括setAdapter，setText等等）
     */
    protected void setInit(){}
    /**
     * 监听
     */
    protected void setListener(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseView = null;
    }

    protected abstract Class<T> getViewClass();
}
