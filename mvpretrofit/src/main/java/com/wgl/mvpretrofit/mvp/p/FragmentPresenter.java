package com.wgl.mvpretrofit.mvp.p;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wgl.mvpretrofit.mvp.v.IDelegate;

/**
 * Presenter层的实现基类 FragmentPresenter
 * @author Biligle
 */
public abstract class FragmentPresenter<T extends IDelegate> extends Fragment {
    public T baseView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            baseView = getViewClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        baseView.create(inflater, container, savedInstanceState);
        baseView.initWidget();
        return baseView.getRootView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
    public void onDestroy() {
        super.onDestroy();
        baseView = null;
    }

    protected abstract Class<T> getViewClass();
}
