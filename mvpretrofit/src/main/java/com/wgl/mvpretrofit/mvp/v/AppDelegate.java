/**
 * 此框架是个人，根据网上代码改写，目前具有局限性，使用者可根据需求添加接口和方法，
 * 如有改动，请联系作者
 * qq：846462358 回答问题：王国立
 * @author wangguoli
 */
package com.wgl.mvpretrofit.mvp.v;

import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.wgl.mvpretrofit.R;

/**
 * 视图层代理的基类
 *
 * @author wangguoli
 */
public abstract class AppDelegate implements IDelegate {
    protected final SparseArray<View> mViews = new SparseArray<View>();
    protected View rootView;
    protected View[] rootView2;
    private int n;

    public abstract int getRootLayoutId();
    public abstract int[] getBrotherLayoutId();

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView2 = new View[getBrotherLayoutId().length];
        for(int i=0; i<getBrotherLayoutId().length; i++){
            int brotherLayoutId = getBrotherLayoutId()[i];
            if(brotherLayoutId != 0){
                rootView2[i] = inflater.inflate(brotherLayoutId, container, false);
            }
        }
        if(getRootLayoutId() != 0){
            rootView = inflater.inflate(getRootLayoutId(), container, false);
        }
    }

    /**
     * 获得根部局
     * @return
     */
    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public View getBrotherView(int n) {
        View v= null;
        try {
            v = rootView2[n];
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            return v;
        }
    }

    /**
     * 获得根部局，内部的控件
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    /**
     * 获得相关布局，内部的控件
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T getBrother(int id,int n) {
        this.n = n;
        return (T) bindBrother(id, n);
    }

    public <T extends View> T bindBrother(int id, int n) {
        T view = (T) mViews.get(id);
        if (view == null) {
            try {
                view = (T) rootView2[n].findViewById(id);
                mViews.put(id, view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    /**
     * 长按事件
     * @param listener
     * @param ids
     */
    public void setOnLongClickListener(View.OnLongClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            if(get(id) != null){
                get(id).setOnLongClickListener(listener);
            }else{
                getBrother(id,n).setOnLongClickListener(listener);
            }
        }
    }
    /**
     * 触碰事件
     * @param listener
     * @param ids
     */
    public void setOnTouchListener(View.OnTouchListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            if(get(id) != null){
                get(id).setOnTouchListener(listener);
            }else{
                getBrother(id,n).setOnTouchListener(listener);
            }
        }
    }
    /**
     * 点击事件(针对根部局中的控件)
     * @param listener
     * @param ids
     */
    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            if(get(id) != null){
                get(id).setOnClickListener(listener);
            }else{
                getBrother(id,n).setOnClickListener(listener);
            }
        }
    }

    /**
     * 获得activity
     * @param <T>
     * @return
     */
    public <T extends Activity> T getActivity() {
        return (T) rootView.getContext();
    }

}
